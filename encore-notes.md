# Encore (encoredev/encore) — Exploration Notes

Repo: https://github.com/encoredev/encore
License: MPL-2.0

## What it is
Encore is an open-source backend framework for building type-safe distributed
systems. You declare APIs, services, and infrastructure (databases, Pub/Sub,
caches, buckets, cron jobs) as code objects. The framework maps those semantic
declarations to concrete implementations per environment, so the same app runs
locally, on Kubernetes/Docker, or on AWS/GCP via Encore Cloud without code
changes.

## Languages / SDKs
- Encore.go (Go)
- Encore.ts (TypeScript)
- Python is on the roadmap
- Codebase itself: ~57% Go, ~40% Rust, ~2% TypeScript

## Key capabilities
- Type-safe service + API definitions
- Declarative infrastructure as code objects
- Local dev CLI with dashboard: distributed tracing, API explorer, service
  catalog, architecture diagrams, DB explorer (no Docker Compose required)
- Deployment targets: Encore Cloud (AWS/GCP auto-provisioning), self-hosted
  Kubernetes, or exported Docker images

## Top-level layout
- `cli/` — `encore` CLI
- `v2/` — v2 framework code (Go)
- `parser/encoding/` — parsing/encoding
- `tsparser/` — TypeScript parser (Rust, hence the large Rust share)
- `runtimes/` — per-language runtimes
- `pkg/` — public Go packages
- `internal/` — internal Go packages
- `proto/` — protobuf definitions
- `supervisor/` — process supervision
- `miniredis/` — in-process Redis for local dev
- `tools/` — build/dev tools
- `e2e-tests/` — end-to-end tests
- `docs/` — documentation
- Config: `go.mod`/`go.sum`, `Cargo.toml`/`Cargo.lock`, `rustfmt.toml`,
  `.prettierrc.toml`, `clippy.toml`, `Cross.toml`, `check.bash`
- `go_llm_instructions.txt`, `ts_llm_instructions.txt` — LLM integration guides

## Mental model
- Go + Rust core builds the compiler/parser/runtime; users write apps in Go or
  TypeScript.
- `tsparser` (Rust) parses TS source to extract infra/service declarations.
- `v2/` is the current-gen Go compiler/parser implementation.
- `runtimes/` implements the runtime glue that each app links against.
- The CLI wires local dev (miniredis, supervisor, dashboard) and cloud deploys.

## Getting started
```
# install
brew install encoredev/tap/encore                      # macOS
curl -L https://encore.dev/install.sh | bash           # Linux
iwr https://encore.dev/install.ps1 | iex               # Windows

# scaffold
encore app create --example=ts/hello-world             # TypeScript
encore app create --example=hello-world                # Go
```
