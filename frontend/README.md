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

## Packaging for Production

To package apps for the local platform:

```bash
npm run package
```

## Testowanie

Aby przetestować fetchowanie i widok listy pozwoliłem sobie skorzystać z json-server

Instalacja:
```bash
npm install -g json-server@0.17.4
```
Koniecznie w tej wersji bo z nową jest coś nie tak

Następnie odpalamy serwer:
```bash
json-server --watch db_test/db.json --port 8000
```

## Co zrobiłem

1. Widok listy sumoków, pozycje pokazują tylko id, imię, wagę i kategorię, bo to jest w sumie najważniejsze, takie szczegóły jak kraj czy wiek zostawiłbym na stronę ze zdjęciem. Na taką stronę przenosimy się po kliknięciu w pozycje. Oczywiście na razie to pusta strona bo nie tego dotyczył ten task
2. Dałem 3 buttony, exit, sortowanie, filtrowanie. Oczywiście dwa ostatnie nie działają bo ten task nie dotyczył filtrowania/sortowania, to tylko taka moja koncepcja jak to może wyglądać.
