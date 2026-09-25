# Registro de aprendizaje

Bitácora corta al cierre de cada sesión: qué se hizo y qué concepto nuevo apareció.

## 24/09/2026 — Estructura inicial

- Se generó el esqueleto del plugin a partir del [plugin-template](https://github.com/HytaleModding/plugin-template) oficial de HytaleModding (Gradle + `com.azuredoom.hytale-tools`).
- Conceptos: qué es un Gradle wrapper (`gradlew`), para qué sirve `manifest.json` y cómo el plugin de Gradle lo genera desde `gradle.properties`, estructura mínima de un `JavaPlugin` con un `AbstractCommand`.
- Pendiente: instalar JDK 25 (hay JDK 21 instalado) antes de poder compilar.

## 25/09/2026 — Primer plugin corriendo en el juego

- Instalado JDK 25 (Azul Zulu) en paralelo al 21, sin afectar otros proyectos. Se seteó `org.gradle.java.home` en la config global de Gradle (`~/.gradle/gradle.properties`), no en el repo, porque es específico de esta máquina.
- Conceptos: Gradle es un orquestador de tareas (no compila él mismo, delega en `javac`); **daemon** (proceso de Gradle) y **toolchain** (JVM usada para compilar el código del proyecto) son dos configuraciones independientes.
- `setupHytaleDev` bajó y decompiló los assets/server de Hytale (requiere login por OAuth device-flow, una vez).
- `runServer` levantó el server local; requirió `/auth login device` (otro OAuth) para poder conectarse como jugador.
- Bug real: `/hylocale` tiraba "sin permisos". Causa: `AbstractCommand` de Hytale le genera un nodo de permiso propio a *todo* comando por defecto, salvo que se llame `requireNoPermission()` explícitamente. Se corrigió en `HyLocaleCommand`.
- **Hito cerrado:** comando `/hylocale` responde en el chat del juego. Fin del objetivo de la Fase 0.
