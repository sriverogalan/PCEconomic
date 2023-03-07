import { jsPDF } from "jspdf";

export function exportToPDF(table, information) {
  jsPDF = new jsPDF();

  // Crear un nuevo documento PDF
  var doc = new jsPDF();

  // Agregar el título de la factura
  doc.setFontSize(22);
  doc.text("Factura nº " + information.id, 20, 20);
  // Agregar las líneas de información de la factura
  doc.setFontSize(12);
  doc.text("ID: " + information.id, 20, 40);
  doc.text("Fecha: " + information.date, 20, 50);
  doc.text("Nombre del Cliente: " + information.name, 20, 60);
  doc.text("Dirección: " + information.direction, 20, 70);
  doc.text("Método de pago: " + information.metodo_pago, 20, 80);
  doc.text("Estado de la factura: " + information.status, 20, 90);

  // Convertir la tabla HTML en una tabla de PDF
  doc.autoTable({
    html: table,
    startY: 110,
  });

  // Agregar el precio del transporte y el precio final
  var precioTransporte = information.transport;
  var precioTotal = information.price;
  doc.setFontSize(14);
  doc.text("Precio del transporte: " + precioTransporte, 20, 220);
  doc.text("Precio total: " + precioTotal, 20, 230);

  // Guardar el PDF en el disco duro
  doc.save(`factura_${information.id}.pdf`);
}
