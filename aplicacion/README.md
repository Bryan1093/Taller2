# Motor Gráfico — Taller 2
**Universidad Central del Ecuador**  
Procesamiento Digital de Imágenes

## Equipo
- Bryan Loya
- _(Nombre 2)_
- _(Nombre 3)_

---

## ▶️ Cómo ejecutar

### Opción 1 — Solo Java (recomendado en máquinas de la U)
Solo necesitás tener **Java 17 o superior** instalado.

```bash
java -jar target/motor-grafico.jar
```

Luego abrí tu navegador en: **http://localhost:8080**

### Opción 2 — Windows (doble clic)
Ejecutá `run.bat` y se abre el navegador automáticamente.

### Opción 3 — Compilar desde fuente (requiere Maven)
```bash
mvn package
java -jar target/motor-grafico.jar
```

---

## 🧪 Filtros implementados

### Básicos
- Original, Grises Clásica, Blanco y Negro, Negativo
- Offset Brillo, Saturación, Transparencia
- Recorte + Estirar Bits, Solo Recorte de Bits
- Tinte Ocre

### Retro
- Vidrio Esmerilado, Desvanecimiento Circular
- Posterización (N niveles)
- Retro 2 (Sin Rojo / Verde / Azul)
- Anular Canal Puro (Rojo / Verde / Azul)
- Grises Retro, RGB 3 Tercios, Espejo, Resaltar Verde

### Espaciales (Convolución)
- Identity, Edge Detection 1/2/3
- Sharpen, Box Blur fijo 3x3
- Box Blur Dinámico NxN, Gaussian Blur

### Generadores (sin imagen de entrada)
- Gradiente Horizontal / Vertical / Radial / Inverso
- Ruido RGB

---

## ⚙️ Requisitos mínimos
- Java 17+
- Navegador moderno (Chrome, Firefox, Edge)
