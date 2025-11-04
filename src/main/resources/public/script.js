// WebSocket connection for real-time price updates
let ws = null;

// Initialize WebSocket connection when page loads
document.addEventListener('DOMContentLoaded', function() {
    console.log("Inicializando conexión WebSocket...");
    
    // Connect to WebSocket
    ws = new WebSocket("ws://localhost:4567/precios");
    
    // Handle connection open
    ws.onopen = function(event) {
        console.log("✅ WebSocket conectado exitosamente");
    };
    
    // Handle incoming messages (price updates)
    ws.onmessage = function(event) {
        console.log("📨 Mensaje recibido:", event.data);
        
        // Parse message format: "ITEM_ID:NEW_PRICE"
        const parts = event.data.split(':');
        if (parts.length === 2) {
            const itemId = parts[0];
            const newPrice = parseFloat(parts[1]);
            
            // Update the price display for this item
            const priceElement = document.getElementById('precio-' + itemId);
            if (priceElement) {
                // Format price with $ symbol
                priceElement.textContent = '$' + newPrice.toFixed(2);
                
                // Add visual feedback with animation
                priceElement.classList.add('updated');
                setTimeout(() => {
                    priceElement.classList.remove('updated');
                }, 500);
                
                console.log(`💰 Precio actualizado para item ${itemId}: $${newPrice.toFixed(2)}`);
            } else {
                console.warn(`⚠️ No se encontró elemento con ID: precio-${itemId}`);
            }
        } else {
            console.warn("⚠️ Formato de mensaje inválido:", event.data);
        }
    };
    
    // Handle connection errors
    ws.onerror = function(error) {
        console.error("❌ Error en WebSocket:", error);
    };
    
    // Handle connection close
    ws.onclose = function(event) {
        console.log("🔌 WebSocket desconectado. Código:", event.code, "Razón:", event.reason);
        
        // Try to reconnect after 3 seconds
        setTimeout(function() {
            console.log("🔄 Intentando reconectar...");
            ws = new WebSocket("ws://localhost:4567/precios");
        }, 3000);
    };
});

// Function to send a bid (price update request)
function enviarPuja(event, itemId) {
    event.preventDefault();
    
    const inputPuja = document.getElementById('puja-' + itemId);
    if (!inputPuja) {
        alert("Error: No se encontró el campo de puja.");
        return;
    }
    
    const nuevoPrecio = parseFloat(inputPuja.value);
    
    // Validate price
    if (isNaN(nuevoPrecio) || nuevoPrecio <= 0) {
        alert("Por favor, introduce un precio válido mayor que 0.");
        return;
    }
    
    // Check WebSocket connection
    if (!ws || ws.readyState !== WebSocket.OPEN) {
        alert("Error: No hay conexión con el servidor. Por favor, recarga la página.");
        return;
    }
    
    // Send bid message to server
    const mensaje = `${itemId}:${nuevoPrecio.toFixed(2)}`;
    console.log("📤 Enviando puja:", mensaje);
    ws.send(mensaje);
    
    // Also send to API endpoint to update the price in the database
    fetch(`/api/items/${itemId}/price`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: nuevoPrecio.toString()
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => {
                throw new Error(err.message || 'Error al actualizar el precio');
            });
        }
        return response.json();
    })
    .then(data => {
        console.log("✅ Precio actualizado en el servidor:", data);
        inputPuja.value = "";
    })
    .catch(error => {
        console.error("❌ Error al actualizar precio:", error);
        alert("Error al actualizar el precio: " + error.message);
    });
}

