// The frontend is served by Spring Boot from the same origin as the API,
// so a relative base ("") is enough. Browser-delivered config is public:
// never put credentials or tokens here.
//
// If you later run this folder from a separate dev server (e.g. Live Server on
// http://localhost:5500), set this to "http://localhost:8080" AND allow that
// origin in the backend. Different port = different origin = CORS applies.
window.API_BASE = "";
