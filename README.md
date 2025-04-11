# 🌍 Countries API

Este proyecto es una API REST que permite **almacenar y consultar datos poblacionales por país**, obtenidos a través de la API pública [https://restcountries.com](https://restcountries.com), y persistidos en una base de datos relacional PostgreSQL.

---

## 🔧 Tecnologías utilizadas

- ☕ Java 17
- ⚙️ Spring Boot 3.4.4
- 🗄️ Spring Data JPA
- 🐘 PostgreSQL 16
- 🐳 Docker & Docker Compose

---

## 🚀 Cómo ejecutar el proyecto

### 🔁 Usar imagen desde Docker Hub

Puedes levantar todo el entorno sin necesidad de compilar nada:

```bash
git clone https://github.com/andresfrdev/countries-api.git
cd countries-api
docker compose up -d
```

> Esto descargará y usará automáticamente la imagen `andresfrdev/countries-api` desde [Docker Hub](https://hub.docker.com/r/andresfrdev/countries-api), junto con la base de datos PostgreSQL definida en `docker-compose.yml`.

---

## 📡 Endpoints disponibles y documentación Swagger

Puedes explorar la API y probar los endpoints en [Swagger UI](http://localhost:8080/swagger-ui/index.html).

| Método | Endpoint                   | Descripción                                                                 |
|--------|----------------------------|-----------------------------------------------------------------------------|
| `POST` | `/api/v1/country`      | Obtiene y guarda datos poblacionales desde la API de terceros              |
| `GET`  | `/api/v1/country`      | Devuelve todos los países y su número de habitantes en formato JSON        |

### 🔁 Ejemplo de respuesta `GET`

```json
[
  {
    "name": "China",
    "population": 1402112000
  },
  {
    "name": "France",
    "population": 67391582
  }
]
```

---

## ⚙️ Configuración por defecto (base de datos)

- **Usuario**: `admin`
- **Contraseña**: `Admin2025`
- **Base de datos**: `sdg`
- **Puerto PostgreSQL**: `5432`

---

¡Gracias por la oportunidad! 🚀
