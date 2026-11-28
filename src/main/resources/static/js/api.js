const API_BASE = window.API_BASE ?? "";

// Carries the HTTP status so callers can tell 404 apart from a network failure.
class ApiError extends Error {
  constructor(message, status) {
    super(message);
    this.name = "ApiError";
    this.status = status; // 0 means "the request never reached the server"
  }
}

async function readErrorMessage(response) {
  try {
    const body = await response.json();
    return body && body.message ? body.message : "";
  } catch {
    return ""; // body was not JSON; fall back to the generic message
  }
}

async function getJson(path) {
  let response;

  try {
    response = await fetch(`${API_BASE}${path}`, {
      headers: { Accept: "application/json" },
    });
  } catch {
    // fetch only rejects when the request could not be made at all
    throw new ApiError("Cannot reach the server. Is the Spring Boot app running?", 0);
  }

  if (!response.ok) {
    const detail = await readErrorMessage(response);
    throw new ApiError(detail || `Request failed (${response.status})`, response.status);
  }

  return response.json();
}

window.api = { getJson, ApiError };
