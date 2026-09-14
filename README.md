# Backend – Comparador de Precios de Supermercados

**Proyecto Final DAW – Backend**

Este backend se encarga de gestionar productos, supermercados y precios, ofreciendo la información a la aplicación frontend para que los usuarios puedan comparar precios en tiempo real y optimizar sus compras.

---

## Tecnologías

![Java](https://img.shields.io/badge/Java-17-informational?style=flat\&logo=java\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-2.7-success?style=flat\&logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.8-blue?style=flat\&logo=apachemaven)
![Docker](https://img.shields.io/badge/Docker-20.10-blue?style=flat\&logo=docker)
![SQL](https://img.shields.io/badge/SQL-Relational-4BAF50?style=flat\&logo=postgresql)

---

## Funcionalidades principales

* Gestión de supermercados y productos
* Registro y actualización de precios
* API REST lista para integrarse con el frontend
* Backup y restauración de base de datos
* Despliegue con Docker para entorno consistente

---

## Estructura del proyecto

```text
src/                 → Código fuente Java (controladores, servicios, repositorios)
target/              → Compilados y artefactos generados
backup.sql           → Backup inicial de la base de datos
docker-compose.yml   → Configuración de contenedores Docker
pom.xml              → Dependencias y plugins de Maven
```

---

## Instalación y ejecución

### Con Maven

```bash
# Clonar repositorio
git clone https://github.com/KevinVk05/backComparador.git

# Entrar en la carpeta
cd backComparador

# Compilar proyecto
mvn clean install

# Ejecutar aplicación
mvn spring-boot:run
```

### Con Docker

```bash
docker-compose up --build
```

La API estará disponible en: `http://localhost:8080`

---

## Notas

* Este backend se integra con el frontend de React para ofrecer una experiencia completa de comparación de precios.
* Diseñado como proyecto final de DAW para demostrar integración de API REST, gestión de base de datos y despliegue con Docker.
