# rikishi frontend

## Install

```bash
npm install
```

## Starting Development

Start the app in the `dev` environment:

```bash
npm start
```

### Lint

```bash
npm run lint
npm run lint-fix    # to auto-correct some issues
```

## Packaging for Production

To package apps for the local platform:

```bash
npm run package
```

## Testowanie

Aby przetestować frontend trzeba skorzystać z json-server

Instalacja:
```bash
npm install -g json-server@0.17.4
```
Koniecznie w tej wersji bo z nową jest coś nie tak

Następnie odpalamy serwer:
```bash
json-server --watch db_test/db.json --port 8000
```
