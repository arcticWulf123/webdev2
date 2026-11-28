(() => {
  const { getJson, ApiError } = window.api;

  const PAGE_SIZE = 5;

  const state = {
    page: 0,
    totalPages: 1,
    categoryId: "",
  };

  const els = {
    categoryFilter: document.querySelector("#category-filter"),
    status: document.querySelector("#list-status"),
    retry: document.querySelector("#retry"),
    table: document.querySelector("#product-table"),
    rows: document.querySelector("#product-rows"),
    prev: document.querySelector("#prev-page"),
    next: document.querySelector("#next-page"),
    pageIndicator: document.querySelector("#page-indicator"),
    detailForm: document.querySelector("#detail-form"),
    detailId: document.querySelector("#detail-id"),
    detailStatus: document.querySelector("#detail-status"),
    detailFields: document.querySelector("#detail-fields"),
  };

  const priceFormatter = new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
  });

  function formatPrice(value) {
    return priceFormatter.format(Number(value));
  }

  function setListStatus(message, kind = "info") {
    els.status.textContent = message;
    els.status.dataset.kind = kind;
    els.retry.hidden = kind !== "error";
  }

  function setDetailStatus(message, kind = "info") {
    els.detailStatus.textContent = message;
    els.detailStatus.dataset.kind = kind;
  }

  function describe(error) {
    if (error instanceof ApiError) return error.message;
    return `Unexpected response: ${error.message}`;
  }

  /* ---------- list ---------- */

  async function loadProducts() {
    setListStatus("Loading…", "loading");
    els.table.hidden = true;
    els.rows.replaceChildren();
    els.prev.disabled = true;
    els.next.disabled = true;

    const params = new URLSearchParams({
      page: String(state.page),
      size: String(PAGE_SIZE),
    });
    if (state.categoryId) params.set("categoryId", state.categoryId);

    try {
      const page = await getJson(`/api/products?${params}`);
      const rows = page.content;

      // The list endpoint returns a Page object, not an array. Guard against
      // a changed contract instead of silently rendering nothing.
      if (!Array.isArray(rows)) throw new Error("Unexpected list response");

      state.totalPages = Number(page.totalPages) || 0;

      renderRows(rows);
      renderPager(page);

      if (rows.length === 0) {
        setListStatus(
          state.categoryId
            ? "No products match this filter."
            : "No products yet.",
          "empty",
        );
      } else {
        setListStatus(
          `Showing ${rows.length} of ${page.totalElements} products.`,
          "info",
        );
      }
    } catch (error) {
      setListStatus(describe(error), "error");
      els.pageIndicator.textContent = "Page –";
    }
  }

  function renderRows(rows) {
    els.rows.replaceChildren();

    if (rows.length === 0) {
      els.table.hidden = true;
      return;
    }

    for (const product of rows) {
      const tr = document.createElement("tr");
      tr.append(
        cell(product.name),
        cell(product.sku),
        cell(product.categoryName),
        cell(formatPrice(product.price), "num"),
        cell(String(product.stockQuantity), "num"),
        statusCell(product.status),
        detailCell(product.id),
      );
      els.rows.append(tr);
    }

    els.table.hidden = false;
  }

  function cell(text, className) {
    const td = document.createElement("td");
    td.textContent = text;
    if (className) td.className = className;
    return td;
  }

  function statusCell(status) {
    const td = document.createElement("td");
    const badge = document.createElement("span");
    badge.className = `badge badge-${String(status).toLowerCase()}`;
    badge.textContent = status;
    td.append(badge);
    return td;
  }

  function detailCell(id) {
    const td = document.createElement("td");
    const button = document.createElement("button");
    button.type = "button";
    button.className = "link-button";
    button.textContent = "View";
    button.addEventListener("click", () => loadProduct(id));
    td.append(button);
    return td;
  }

  function renderPager(page) {
    const number = Number(page.number) || 0;
    const totalPages = state.totalPages;

    els.pageIndicator.textContent = totalPages === 0
      ? "Page 0 of 0"
      : `Page ${number + 1} of ${totalPages}`;

    els.prev.disabled = number <= 0;
    els.next.disabled = number >= totalPages - 1;
  }

  /* ---------- categories ---------- */

  async function loadCategories() {
    try {
      const categories = await getJson("/api/categories");

      // Raw array here, not a Page. Different shape from the product list.
      if (!Array.isArray(categories)) throw new Error("Unexpected category response");

      for (const category of categories) {
        const option = document.createElement("option");
        option.value = String(category.id);
        option.textContent = category.name;
        els.categoryFilter.append(option);
      }
    } catch (error) {
      els.categoryFilter.disabled = true;
      setListStatus(`Category filter unavailable. ${describe(error)}`, "error");
    }
  }

  /* ---------- detail ---------- */

  async function loadProduct(id) {
    setDetailStatus("Loading…", "loading");
    els.detailFields.hidden = true;

    try {
      const product = await getJson(`/api/products/${encodeURIComponent(id)}`);
      renderDetail(product);
      setDetailStatus("", "info");
    } catch (error) {
      if (error instanceof ApiError && error.status === 404) {
        setDetailStatus(`No product exists with ID ${id}.`, "not-found");
      } else {
        setDetailStatus(describe(error), "error");
      }
    }
  }

  function renderDetail(product) {
    document.querySelector("#detail-name").textContent = product.name;
    document.querySelector("#detail-sku").textContent = product.sku;
    document.querySelector("#detail-category").textContent = product.categoryName;
    document.querySelector("#detail-price").textContent = formatPrice(product.price);
    document.querySelector("#detail-stock").textContent = String(product.stockQuantity);
    document.querySelector("#detail-status-value").textContent = product.status;
    document.querySelector("#detail-id-value").textContent = String(product.id);

    els.detailFields.hidden = false;
  }

  /* ---------- wiring ---------- */

  els.categoryFilter.addEventListener("change", () => {
    state.categoryId = els.categoryFilter.value;
    state.page = 0; // a filter change can leave the old page out of range
    loadProducts();
  });

  els.prev.addEventListener("click", () => {
    if (state.page > 0) {
      state.page -= 1;
      loadProducts();
    }
  });

  els.next.addEventListener("click", () => {
    if (state.page < state.totalPages - 1) {
      state.page += 1;
      loadProducts();
    }
  });

  els.retry.addEventListener("click", loadProducts);

  els.detailForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const raw = els.detailId.value.trim();
    if (!raw) return;
    loadProduct(raw);
  });

  loadProducts();
  loadCategories();
})();
