# Guía Técnica: Generación de Gradientes en Java

Este documento explica la lógica matemática y de programación aplicada en el archivo `Gradientes.java`.

## 1. El Concepto de "Porcentaje" (`porc`)
La base de todos los gradientes es un valor decimal entre **0.0 y 1.0**. Este valor representa la posición relativa del píxel actual respecto al total.

*   **0.0**: El inicio del gradiente (color inicial).
*   **1.0**: El final del gradiente (color final).

---

## 2. Análisis de los Casos (`switch`)

### `case "HORIZONTAL_NORMAL"`
*   **Eje:** Horizontal (`x`).
*   **Fórmula:** `porc = (double) x / ancho;`
*   **Color:** `r = g = b = (int) (255 * porc);`
*   **Funcionamiento:** A medida que nos movemos de izquierda a derecha, el valor de `x` crece. Como el porcentaje sube, los valores de R, G y B suben por igual, creando una transición de **Negro a Blanco**.

### `case "HORIZONTAL_INVERSO_ROJO_AZUL"`
*   **Eje:** Horizontal (`x`).
*   **Fórmula:** `porc = (double) x / ancho;`
*   **Lógica de Color:**
    *   `r = 255 * porc`: El Rojo crece hacia la derecha. (0 en la izquierda, 255 en la derecha).
    *   `b = 255 * (1 - porc)`: El Azul decrece hacia la derecha. (255 en la izquierda, 0 en la derecha).
*   **Funcionamiento:** Creamos un efecto de "sube y baja". En la izquierda domina el azul y en la derecha el rojo. En el medio (porc = 0.5) se mezclan creando un violeta.

### `case "VERTICAL_NORMAL"`
*   **Eje:** Vertical (`y`).
*   **Fórmula:** `porc = (double) y / alto;`
*   **Lógica de Color:** Se asignan proporciones fijas (100, 200, 255) multiplicadas por el porcentaje.
*   **Funcionamiento:** El cambio de color ocurre al bajar por la imagen. Como `y` aumenta hacia abajo, el color se va aclarando hacia un tono celeste/azul.

### `case "VERTICAL_INVERSO"`
*   **Eje:** Vertical (`y`).
*   **Fórmula:** `porc = 1.0 - ((double) y / alto);`
*   **Funcionamiento:** Al restar el progreso de `1.0`, invertimos la dirección. El porcentaje es **1.0** en la parte inferior (`y = alto`) y **0.0** en la superior (`y = 0`). Esto hace que el gradiente "nazca" desde abajo hacia arriba.

### `case "RADIAL"`
*   **Lógica:** Distancia al centro.
*   **Fórmulas:**
    1.  Centro: `cx = ancho / 2`, `cy = alto / 2`.
    2.  Distancia actual: Usamos Pitágoras: $\sqrt{(x-cx)^2 + (y-cy)^2}$
    3.  Distancia Máxima: Es la distancia desde el centro hasta una de las esquinas.
*   **Funcionamiento:**
    *   En el centro del círculo, la distancia es 0, por lo que `porc` es 0.
    *   Usamos `(1 - porc)` para que el centro sea **Blanco** (255) y los bordes sean **Negros** (0).

---

## 3. Resumen de Fórmulas Clave

| Concepto | Operación | Por qué se usa |
| :--- | :--- | :--- |
| **Casting `(double)`** | `(double) x / ancho` | Evita que Java redondee a 0 la división entre enteros. |
| **Escalamiento** | `255 * porc` | Convierte un valor 0.0-1.0 al rango de color RGB (0-255). |
| **Inversión** | `1.0 - porc` | Invierte la dirección del gradiente o del canal de color. |
| **Empaquetado** | `(r << 16) \| (g << 8) \| b` | Combina los tres canales en un solo entero de 32 bits. |

---

> **Nota para el examen:** Acordate siempre que el bucle `for` recorre la imagen píxel por píxel. El código dentro del `switch` se pregunta: "¿En qué posición estoy?" y luego decide de qué color pintar ese puntito específico.
