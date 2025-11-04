# Checklist - Sprint 3
## Filtros y WebSocket para Actualizaciones de Precio en Tiempo Real

Este checklist asegura que todas las funcionalidades requeridas para el Sprint 3 estén implementadas y funcionando correctamente.

---

## ✅ Funcionalidades Implementadas

### Filtros para Items

- [x] **Filtro por Categoría**
  - Implementado en `FilterService.applyFilters()`
  - Disponible en la interfaz web (`tienda.mustache`)
  - Permite filtrar items por categoría específica (ej: "Juguetes", "Comics")

- [x] **Filtro por Precio Mínimo**
  - Implementado en `FilterService.applyFilters()`
  - Disponible en la interfaz web con campo numérico
  - Valida que el precio sea un número válido

- [x] **Filtro por Precio Máximo**
  - Implementado en `FilterService.applyFilters()`
  - Disponible en la interfaz web con campo numérico
  - Valida que el precio sea un número válido

- [x] **Filtro por Disponibilidad**
  - Implementado en `FilterService.applyFilters()`
  - Disponible en la interfaz web con dropdown
  - Permite filtrar por items disponibles, no disponibles, o todos

- [x] **Integración de Filtros**
  - `ItemService.getAllItems()` acepta todos los parámetros de filtro
  - `ItemController.renderTienda()` procesa los query params y pasa los filtros al servicio
  - Los filtros pueden combinarse (ej: categoría + rango de precio)

---

### WebSocket para Actualizaciones de Precio en Tiempo Real

- [x] **Implementación del WebSocket**
  - `PriceWebSocket.java` implementado con anotaciones de Jetty WebSocket
  - Manejo de conexiones, desconexiones y mensajes
  - Cola thread-safe (`ConcurrentLinkedQueue`) para sesiones activas

- [x] **Servicio de Actualización de Precios**
  - `PriceUpdateService.java` implementado
  - Integra `ItemService` para actualizar precios en la base de datos
  - Integra `PriceWebSocket` para notificar a todos los clientes conectados

- [x] **Ruta API para Actualización de Precios**
  - Ruta POST `/api/items/:id/price` implementada en `Main.java`
  - Valida que el precio sea un número válido y positivo
  - Actualiza el precio y notifica a todos los clientes vía WebSocket

- [x] **Frontend - JavaScript**
  - `script.js` implementado con lógica completa del WebSocket
  - Conexión automática al cargar la página
  - Manejo de mensajes recibidos (actualización de precios)
  - Función `enviarPuja()` para enviar nuevas pujas
  - Feedback visual cuando se actualiza un precio
  - Reconexión automática si se pierde la conexión

- [x] **Integración Frontend-Backend**
  - Template `tienda.mustache` incluye formularios para pujar
  - Cada item tiene su propio formulario de puja
  - Los precios se actualizan en tiempo real sin recargar la página
  - El script.js se carga desde `/public/script.js`

---

## 🔍 Verificación de Calidad

### Funcionalidad
- [x] Los filtros funcionan correctamente de forma individual
- [x] Los filtros pueden combinarse correctamente
- [x] El WebSocket se conecta correctamente al iniciar la aplicación
- [x] Las actualizaciones de precio se propagan a todos los clientes conectados
- [x] La actualización de precio se persiste en el backend (ItemService)
- [x] El frontend muestra visualmente las actualizaciones de precio

### Manejo de Errores
- [x] Validación de precios inválidos (números negativos, no numéricos)
- [x] Manejo de errores de conexión WebSocket
- [x] Mensajes de error claros al usuario
- [x] Reconexión automática del WebSocket

### Arquitectura
- [x] Separación de responsabilidades (Service, Controller, WebSocket)
- [x] Uso de `FilterService` para centralizar lógica de filtrado
- [x] Método estático `broadcast()` en `PriceWebSocket` para acceso global
- [x] Integración limpia entre servicios

---

## 📝 Archivos Modificados/Creados en Sprint 3

### Archivos Java
- ✅ `services/FilterService.java` - Servicio de filtrado
- ✅ `services/PriceUpdateService.java` - Servicio de actualización de precios
- ✅ `websocket/PriceWebSocket.java` - Handler del WebSocket
- ✅ `controllers/ItemController.java` - Actualizado para soportar todos los filtros
- ✅ `services/ItemService.java` - Actualizado para usar todos los filtros
- ✅ `Main.java` - Agregada ruta API para actualización de precios

### Archivos Frontend
- ✅ `public/script.js` - Lógica completa del WebSocket
- ✅ `templates/tienda.mustache` - Formularios de filtros mejorados y pujas

### Documentación
- ✅ `docs/checklist.md` - Este archivo

---

## 🚀 Pruebas Recomendadas

### Pruebas Manuales

1. **Filtros:**
   - Filtrar por categoría: `/tienda?categoria=Juguetes`
   - Filtrar por precio máximo: `/tienda?precioMax=50`
   - Filtrar por precio mínimo: `/tienda?precioMin=20`
   - Filtrar por disponibilidad: `/tienda?disponible=true`
   - Combinar filtros: `/tienda?categoria=Juguetes&precioMin=10&precioMax=100&disponible=true`

2. **WebSocket:**
   - Abrir múltiples pestañas del navegador en `/tienda`
   - Hacer una puja en una pestaña
   - Verificar que el precio se actualiza en todas las pestañas abiertas
   - Verificar que el precio se actualiza en la base de datos (recargar la página)

3. **API:**
   - Probar POST `/api/items/:id/price` con un precio válido
   - Verificar respuesta JSON con el item actualizado
   - Probar con precios inválidos (negativos, no numéricos) y verificar errores

---

## ✅ Criterios de Aceptación

- [x] ✅ Los usuarios pueden filtrar items por categoría
- [x] ✅ Los usuarios pueden filtrar items por rango de precio (mínimo y máximo)
- [x] ✅ Los usuarios pueden filtrar items por disponibilidad
- [x] ✅ Los filtros pueden combinarse
- [x] ✅ Las actualizaciones de precio se propagan en tiempo real a todos los clientes
- [x] ✅ Las actualizaciones de precio se persisten en el backend
- [x] ✅ El frontend muestra feedback visual cuando se actualiza un precio
- [x] ✅ El WebSocket maneja errores y reconexiones correctamente

---

## 📌 Notas Finales

Este Sprint 3 completa la funcionalidad principal del proyecto, agregando:
- Sistema de filtrado avanzado para items
- Comunicación en tiempo real mediante WebSockets
- Actualización de precios en tiempo real sin recargar la página

Todas las funcionalidades están implementadas y probadas según los requisitos del reto.

