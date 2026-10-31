/**
 * Produce los SVG estaticos de UML_GUIDE.md sin depender del visor Mermaid.
 * Ejecutar desde la raiz: node docs/uml/generar-ejemplos.mjs
 * Solo usa modulos incluidos en Node.js.
 */
import { writeFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

const destino = dirname(fileURLToPath(import.meta.url));
const esc = (valor) => String(valor).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
const estilo = `<style>
  .caja{fill:#f0f5ff;stroke:#3972bf;stroke-width:2}
  .division{stroke:#3972bf;stroke-width:1.4}
  .nombre{fill:#152238;font:bold 23px Arial,sans-serif;text-anchor:middle}
  .stereotipo{fill:#40526b;font:italic 17px Arial,sans-serif;text-anchor:middle}
  .texto{fill:#202936;font:19px Arial,sans-serif}
  .linea{stroke:#202936;stroke-width:2.8;fill:none}
  .discontinua{stroke-dasharray:9 7}
  .triangulo{stroke:#202936;stroke-width:2.8;fill:white}
</style>`;

function svg(nombre, descripcion, elementos, alto = 268) {
  return `<svg xmlns="http://www.w3.org/2000/svg" width="1060" height="${alto}" viewBox="0 0 1060 ${alto}" role="img" aria-labelledby="titulo descripcion">
<title id="titulo">${esc(nombre)}</title><desc id="descripcion">${esc(descripcion)}</desc>
${estilo}
${elementos.join('\n')}
</svg>\n`;
}

function caja(modelo, x, { compacta = false, ancho = 350 } = {}) {
  const y = compacta ? 93 : 24;
  const alto = compacta ? 96 : 220;
  const nombreY = compactoNombreY(modelo, compacta);
  const centro = x + ancho / 2;
  const piezas = [`<rect class="caja" x="${x}" y="${y}" width="${ancho}" height="${alto}" rx="2"/>`];
  piezas.push(`<text class="nombre" x="${centro}" y="${nombreY}">${esc(modelo.nombre)}</text>`);
  if (modelo.estereotipo) piezas.push(`<text class="stereotipo" x="${centro}" y="${nombreY + 25}">${esc(modelo.estereotipo)}</text>`);
  if (compacta) return piezas.join('\n');
  const divisor = modelo.estereotipo ? 99 : 83;
  piezas.push(`<line class="division" x1="${x}" y1="${divisor}" x2="${x + ancho}" y2="${divisor}"/>`);
  if (modelo.literales) {
    modelo.literales.forEach((literal, i) => {
      piezas.push(`<text class="texto" x="${x + 18}" y="${divisor + 30 + 30 * i}">${esc(literal)}</text>`);
    });
    return piezas.join('\n');
  }
  piezas.push(`<line class="division" x1="${x}" y1="167" x2="${x + ancho}" y2="167"/>`);
  (modelo.atributos ?? []).slice(0, 2).forEach((linea, i) => {
    piezas.push(`<text class="texto" x="${x + 18}" y="${divisor + 30 + 26 * i}">${esc(linea)}</text>`);
  });
  (modelo.metodos ?? []).slice(0, 2).forEach((linea, i) => {
    piezas.push(`<text class="texto" x="${x + 18}" y="${194 + 25 * i}">${esc(linea)}</text>`);
  });
  return piezas.join('\n');
}

function compactoNombreY(modelo, compacta) {
  if (compacta) return 150;
  return modelo.estereotipo ? 58 : 61;
}

function relacion(tipo) {
  const izquierda = 374, derecha = 686, y = 141;
  if (tipo === 'herencia' || tipo === 'realizacion') {
    const linea = `<line class="linea ${tipo === 'realizacion' ? 'discontinua' : ''}" x1="408" y1="${y}" x2="${derecha}" y2="${y}"/>`;
    const triangulo = `<path class="triangulo" d="M${izquierda} ${y} L408 123 L408 159 Z"/>`;
    return [linea, triangulo].join('\n');
  }
  if (tipo === 'composicion') {
    const linea = `<line class="linea" x1="416" y1="${y}" x2="${derecha}" y2="${y}"/>`;
    const rombo = `<path d="M${izquierda} ${y} L395 128 L416 ${y} L395 154 Z" fill="#202936" stroke="#202936" stroke-width="2.8"/>`;
    return [linea, rombo].join('\n');
  }
  const linea = `<line class="linea ${tipo === 'dependencia' ? 'discontinua' : ''}" x1="${izquierda}" y1="${y}" x2="${derecha}" y2="${y}"/>`;
  const flecha = `<path class="linea" d="M670 128 L${derecha} ${y} L670 154"/>`;
  return [linea, flecha].join('\n');
}

function dosClases({ archivo, titulo, descripcion, izquierda, derecha, tipo, compacta = false }) {
  const contenido = [caja(izquierda, 24, { compacta }), caja(derecha, 686, { compacta }), relacion(tipo)];
  writeFileSync(join(destino, archivo), svg(titulo, descripcion, contenido), 'utf8');
}

function unaClase({ archivo, titulo, descripcion, modelo }) {
  writeFileSync(join(destino, archivo), svg(titulo, descripcion, [caja(modelo, 312, { ancho: 436 })]), 'utf8');
}

const dos = [
  { archivo:'notificador-interfaz.svg', titulo:'Realizacion de interfaz', descripcion:'NotificadorEmail implementa el contrato Notificador mediante linea discontinua y triangulo hueco.',
    izquierda:{nombre:'Notificador',estereotipo:'«interface»',metodos:['+ enviar(mensaje: String): void']}, derecha:{nombre:'NotificadorEmail',metodos:['+ enviar(mensaje: String): void']},tipo:'realizacion' },
  { archivo:'figura-abstracta.svg', titulo:'Clase abstracta', descripcion:'Circulo hereda de la clase abstracta Figura.',
    izquierda:{nombre:'Figura',estereotipo:'«abstract»',metodos:['+ calcularArea(): double']}, derecha:{nombre:'Circulo',metodos:['+ calcularArea(): double']},tipo:'herencia' },
  { archivo:'animal-perro.svg', titulo:'Herencia entre Animal y Perro', descripcion:'Perro hereda de Animal y el triangulo hueco apunta a Animal.',
    izquierda:{nombre:'Animal'},derecha:{nombre:'Perro'},tipo:'herencia',compacta:true },
  { archivo:'pedido-cliente.svg', titulo:'Asociacion entre Pedido y Cliente', descripcion:'Pedido conoce Cliente mediante asociacion navegable.',
    izquierda:{nombre:'Pedido'},derecha:{nombre:'Cliente'},tipo:'asociacion',compacta:true },
  { archivo:'casa-habitacion.svg', titulo:'Composicion entre Casa y Habitacion', descripcion:'El rombo relleno junto a Casa indica que contiene a Habitacion como parte de su composicion.',
    izquierda:{nombre:'Casa'},derecha:{nombre:'Habitacion'},tipo:'composicion',compacta:true },
  { archivo:'factura-impresora.svg', titulo:'Dependencia entre Factura e Impresora', descripcion:'Factura utiliza Impresora de forma temporal mediante dependencia discontinua.',
    izquierda:{nombre:'Factura'},derecha:{nombre:'Impresora'},tipo:'dependencia',compacta:true },
  { archivo:'persona-estudiante.svg', titulo:'Herencia entre Persona y Estudiante', descripcion:'Estudiante hereda de Persona y conserva sus propios atributos.',
    izquierda:{nombre:'Persona',atributos:['# nombre: String'],metodos:['+ getNombre(): String']},derecha:{nombre:'Estudiante',atributos:['- carrera: String'],metodos:['+ getCarrera(): String']},tipo:'herencia' },
  { archivo:'repositorio-realizacion.svg', titulo:'Realizacion entre Repositorio y RepositorioSql', descripcion:'RepositorioSql implementa los metodos declarados en la interfaz Repositorio.',
    izquierda:{nombre:'Repositorio',estereotipo:'«interface»',metodos:['+ guardar(dato: String): void','+ buscarPorId(id: int): String']},derecha:{nombre:'RepositorioSql',metodos:['+ guardar(dato: String): void','+ buscarPorId(id: int): String']},tipo:'realizacion' },
  { archivo:'reporte-impresora.svg', titulo:'Dependencia entre Reporte e Impresora', descripcion:'Reporte recibe Impresora en el metodo generar, sin almacenarla como atributo.',
    izquierda:{nombre:'Reporte',metodos:['+ generar(i: Impresora): void']},derecha:{nombre:'Impresora',metodos:['+ imprimir(texto: String): void']},tipo:'dependencia' },
];
for (const d of dos) dosClases(d);

unaClase({ archivo:'estado-pedido-enum.svg', titulo:'Enumeracion EstadoPedido', descripcion:'Valores permitidos PENDIENTE, PAGADO y CANCELADO.',
  modelo:{nombre:'EstadoPedido',estereotipo:'«enumeration»',literales:['PENDIENTE','PAGADO','CANCELADO']} });
unaClase({ archivo:'pedido-resumen-record.svg', titulo:'Record PedidoResumen', descripcion:'Convencion grafica de record Java con campos finales y accesores.',
  modelo:{nombre:'PedidoResumen',estereotipo:'«record» (convencion)',atributos:['- id: long','- estado: EstadoPedido'],metodos:['+ id(): long','+ estado(): EstadoPedido']} });

const lollipop = [
  caja({nombre:'NotificadorEmail',metodos:['+ enviar(mensaje: String): void']}, 180, {ancho:410}),
  `<line class="linea" x1="590" y1="141" x2="730" y2="141"/>`,
  `<circle cx="752" cy="141" r="21" fill="#f0f5ff" stroke="#202936" stroke-width="2.8"/>`,
  `<text class="texto" x="790" y="147">Notificador</text>`
];
writeFileSync(join(destino,'notificador-lollipop.svg'),svg('Interfaz lollipop','NotificadorEmail proporciona la interfaz Notificador representada por un circulo.',lollipop),'utf8');
