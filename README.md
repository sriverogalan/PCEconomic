## Autores

- Miquel Angel Amengual Sastre
- Sergi Rivero Galan

## Descripción

Nuestro proyecto se basa en una tienda online que permite gestionar los productos, categorías y pedidos.
La dirección de la tienda será pceconomic.me y el nombre de la tienda será PCEconomic. Tendremos una tienda online en la que se podrán comprar productos relacionados con la informática, como por ejemplo ordenadores, componentes, periféricos, etc.

## Stakeholders:

- Administrador
- Usuario registrado
- Usuario público

## Requisitos funcionales:

- El administrador debe poder dar de alta, baja, modificar y listar categorías. Una categoría consta de un nombre y puede contener subcategorías (y éstas también pueden contener otras subcategorías).
- El administrador debe poder dar de alta, baja, modificar y listar propiedades de artículos. Las propiedades constan de un nombre y tipo (texto, enumerado, número…). Por ejemplo: Talla puede ser un enumerado (S, XS, M, XL, XXL).
- El administrador debe poder dar de alta, baja, modificar y listar artículos de la tienda. Los artículos pueden tener una o varias propiedades. Por ejemplo: una camiseta puede tener color, talla, imagen…
- El administrador debe poder controlar el stock de cada artículo. Este stock no sólo debe ir por artículo, sino por artículo + propiedades. Por ejemplo: puede haber 3 camisetas talla XS amarillo, 2 camisetas talla L amarillo y 5 camisetas talla XS verde.
- Los usuarios registrados deben poder marcar un artículo y un tamaño no disponible para recibir una notificación cuando este artículo esté disponible.
- El sistema debe tener un algoritmo de recomendaciones personalizado por usuarios registrados y usuarios públicos.
- El sistema debe tener un cálculo de gastos de envío según tamaño o peso y según el destino, teniendo en cuenta destinos internacionales (tabla de tarifas de un mensajero)
- El usuario público y el usuario registrado debe poder consultar todos los artículos disponibles de la tienda.
- El usuario registrado puede comprar y realizar el seguimiento de un pedido de la tienda. Cada compra debe notificar por correo electrónico al usuario así como cada cambio de estado del seguimiento.
- El administrador debe poder importar la información de los artículos a partir de un archivo xml o csv. Este artículo será proporcionado por los fabricantes o marcas. Si un artículo ya existe se debe actualizar su precio, si éste ha cambiado.
- Los usuarios registrados deben tener poder consultar los pedidos anteriores.
- Los usuarios registrados deben poder pagar a través de Paypal y una pasarela de pago de un banco.
- El administrador debe poder sacar un listado en Excel de los árculos filtrados y ordenados por varios criterios de los artículos y propiedades.
- Un usuario público se puede registrar. Durante el proceso de registro se debe validar la dirección de correo.

## Requisitos no funcionales:

- El sistema debe ser seguro.
- El sistema debe tener una buena usabilidad tanto en desktop como en móvil.
