# Política de traducciones

**Regla fija: permiso antes de trabajar, nunca después.**

Antes de traducir cualquier plugin, hay que verificar su licencia y, si no lo permite explícitamente, pedirle permiso al autor. No se empieza a traducir "por las dudas" esperando el permiso después.

## Cómo se verifica un plugin

1. Leer la licencia del repositorio o de la ficha en CurseForge/BuiltByBit.
2. Si la licencia permite explícitamente traducciones o redistribución de archivos de idioma derivados → se puede empezar.
3. Si la licencia no lo aclara o es restrictiva → contactar al autor y esperar la respuesta antes de tocar el plugin.
4. Cargar el resultado en la tabla de abajo.

## Estado de los plugins

| Plugin | Autor | Licencia | ¿Ya trae español? | Permiso | Estado | Fecha |
|---|---|---|---|---|---|---|
| LuckPerms | lucko | MIT | Sí (es-ES 100%, servidor de traducciones propio) | No requerido | Descartado: ya traducido | 25/09/2026 |
| Ecotale | MichiWeon | All Rights Reserved (desde 1.0.6; antes MIT) | Sí (es-ES incluido) | — | Descartado: ya traducido | 25/09/2026 |
| HyCitizens | Electro Games | Source-available: publicar derivados en CurseForge/BuiltByBit requiere permiso escrito | No | Pedido el 25/09 ([issue #23](https://github.com/ElectroGamesDev/HyCitizens/issues/23)) | Esperando respuesta | 25/09/2026 |
| OrbisGuard | wiflow | All Rights Reserved | Sin verificar (código cerrado) | Pedido el 25/09 en `#mods-questions` del Discord de WiFlow (el formulario de tickets estaba caído) | Esperando respuesta | 25/09/2026 |
| NPC Dialog | Hyronix | All Rights Reserved | Sin verificar (código cerrado) | Pendiente de pedir | Sin empezar | 25/09/2026 |
| TaleQuests | AselStudios | Pago, sin licencia pública | Anuncia 7 idiomas, sin verificar si incluye español | — | Verificar ficha en BuiltByBit | 25/09/2026 |
| EliteEssentials | EliteScouter | MIT según CurseForge (el repo no tiene archivo LICENSE) | No | Aviso por cortesía el 25/09 ([issue #70](https://github.com/EliteScouter/EliteEssentials/issues/70)) | Candidato principal, esperando respuesta | 25/09/2026 |
| Hyssential | HytaleModding / Leclowndu93150 | MIT según CurseForge; repo original CC0 | No (trae `en.json` y `fr.json`) | Aviso por cortesía el 25/09 ([issue #8](https://github.com/HytaleModding/Hyssentials/issues/8)) | Candidato principal, esperando respuesta | 25/09/2026 |
| Essentials Plus | fof1092 | All Rights Reserved | Sin verificar (usa `.lang` nativo de Hytale) | Pendiente de pedir | Candidato secundario | 25/09/2026 |
| HyEssentialsX | MystifiedSky | All Rights Reserved | Sí (`es-es.json` incluido) | — | Descartado: ya traducido | 25/09/2026 |

### Notas del relevamiento

- **HyCitizens:** 264 textos fijos en el código (`Message.raw`) y solo 4 claves en `.lang`. Traducirlo con el sistema nativo exige que el autor migre sus textos a claves. Su licencia permite mandarle pull requests.
- **NPC Dialog:** última actualización 17/02/2026; verificar compatibilidad con el server 0.6.8 antes de invertir tiempo. Los textos de los diálogos los escribe el admin; lo traducible es la interfaz del plugin.
- **OrbisGuard:** activo (actualizado 29/08/2026). Los mensajes por región (`greet-title`, `deny-message`) ya los configura el admin; lo traducible son los mensajes del sistema y los comandos.
- **EliteEssentials:** más de 500 mensajes en `messages.json`, pensado para traducirse. Ya hay dueños de servers hispanos que lo traducen a mano (issue #13, tildes y ñ, corregido por el autor en 1.1.4).
- **Hyssential:** sistema de idiomas propio en JSON (`lang/en.json`, `lang/fr.json`). Sumar `es.json` es directo y encaja como pull request al repo.
- **Contactos:** EliteEssentials → issues de `EliteScouter/EliteEssentials`; Hyssential → issues de `HytaleModding/Hyssentials` o Discord de HytaleModding; HyCitizens → issues de `ElectroGamesDev/HyCitizens`; OrbisGuard → Discord de wiflow (usuario `w1fl0w`); NPC Dialog → discord.gg/DRxwt8Qg2W.

### Competencia (relevada el 25/09/2026)

- **Juego base traducido al español:** mercado saturado (unos 12 proyectos en CurseForge; el mayor, "Spanish Translation" de Plexu5, tiene 144K descargas). HyLocale ES no compite ahí.
- **Hytale Spanish Project-MODS [HSPM]** (Lord_Khaoss): dice traducir 332 mods (sobre todo gameplay). 1.7K descargas, sin actualizar desde el 22/02/2026 y con pasos de instalación manual. Es la debilidad del paquete externo: se desactualiza con cada versión del mod original.

## Formato de traducción

Decidido el 25/09/2026, después de investigar cómo maneja los idiomas Hytale.

**Idioma del jugador.** El cliente de Hytale no ofrece español, así que HyLocale ES le asigna `es-ES` desde el servidor a los jugadores que llegan con el cliente en inglés. Cada jugador puede elegir con `/idioma es` o `/idioma en`. El idioma por defecto se configura en `DefaultLanguage`.

**Cómo se traduce cada plugin**, según cómo guarda sus textos:

| Tipo | Cómo lo detectamos | Cómo lo traducimos |
|---|---|---|
| A. Usa el sistema de idiomas de Hytale | Trae archivos `Server/Languages/en-US/*.lang` | HyLocale ES incluye el `es-ES/*.lang` equivalente. Solo completa lo que falta: si el plugin ya trae su propio `es-ES`, el suyo tiene prioridad. |
| B. Tiene un sistema propio (JSON, YAML) | Archivos de idioma o mensajes en su configuración | Si el plugin soporta varios idiomas, se aporta el español al proyecto original (pull request). Si tiene un solo archivo de mensajes, se distribuye el archivo traducido con instrucciones. |
| C. Texto fijo en el código (`Message.raw`) | No hay archivo que traducir | No se puede traducir desde afuera. Se le ofrece al autor pasar sus textos a claves de traducción. |

**Lo que ningún plugin puede traducir:** los menús del propio cliente (opciones, pantalla principal). Viven en la PC de cada jugador.

## Guía de estilo

Decidida el 25/09/2026 y validada contra el mercado (ver "Evidencia" al final de esta sección).

- **El término popular gana al de diccionario.** Si la comunidad ya eligió una palabra, usamos esa, aunque exista una "más correcta". Lo conocido da confianza y hace que el jugador se sienta en casa.
- **La misma palabra para el mismo concepto, siempre.** Mezclar "casa" y "hogar" en un mismo plugin es el error más común del mercado: no lo repetimos.
- **Español neutro con tuteo.** El mismo texto lo leen jugadores de toda Hispanoamérica y España: nada de voseo ("usá") ni vosotros ("usad"). Se escribe "usa", "escribe", "puedes".
- **Sin regionalismos.** Se prefieren palabras que se entiendan en todos lados.
- **Tono claro, directo y amable**, como el original. Frases cortas: el espacio en pantalla es limitado.
- **Lenguaje inclusivo cuando sea natural:** "Te damos la bienvenida" en lugar de "Bienvenido/a".
- **Ortografía completa:** tildes, ñ y signos de apertura (¿ ¡) siempre. La fuente del juego los soporta.
- **Mayúscula solo al inicio** de títulos y botones ("Configuración del servidor", no "Configuración Del Servidor").

**Nunca se traduce:**
- Comandos y sus argumentos (`/home`, `/warp`, `/rg flag`).
- Marcadores de posición (`{player}`, `{0}`, `%s`) y códigos de color o formato.
- Nombres propios del juego y de los plugins (Hytale, Orbis, Kweebec, EliteEssentials).

**Glosario.** Se amplía a medida que traducimos, siempre con evidencia de uso.

| Inglés | Español | Nota |
|---|---|---|
| server | servidor | |
| player | jugador | |
| spawn, warp, kit, PvP, TPA | se mantienen | Ninguna traducción analizada los traduce |
| home | casa | Más usado que "hogar" (41 contra 19). En comandos se mantiene `/home` |
| claim (verbo) | reclamar | "Reclamar este terreno", "reclamar un kit" |
| claimed | reclamado / reclamada | |
| claim (el área protegida), plot | terreno | "Terreno" es lo más usado en Latinoamérica. "Parcela" suena a España |
| region | región | |
| teleport | teletransportar | |
| money | dinero | |
| balance | saldo | "Tu saldo: 100". "Balance" casi no se usa |
| permission | permiso | |
| cooldown | tiempo de espera | O con el verbo: "Debes esperar {time}" |

**Evidencia (25/09/2026).** Se analizaron las traducciones al español de EssentialsX, LuckPerms, mcMMO, Jobs Reborn y Towny (versiones es-ES, es-MX y es-AR) de Minecraft, más HyEssentialsX de Hytale, contando solo los textos que ve el jugador:

- **Trato:** 8 de 8 archivos usan tú. Ninguno usa vos ni vosotros, ni siquiera la versión argentina de Towny.
- **Claim:** "reclamar" aparece 111 veces, "reclamado/a" 50, "terreno" 259; "reclamo" solo 34.
- **Calidad del mercado:** la única traducción de un plugin de Hytale tiene caracteres rotos, líneas sin traducir y errores de traducción automática (un botón *claim* traducido como "Afirmar").
