# Generar el Plug-in desde el código fuente #

VIOLET-OWL está disponible en un fichero .jar listo para usar. Pero si de todas formas quieres generar el plug-in desde el código fuente sigue los pasos de este manual.

Si no quieres complicarte la vida y lo que deseas es sólo instalar el plug-in, sigue las instrucciones de esta página: [InstallPlugin](InstallPlugin.md).

## Pasos Previos ##
Descarga e instala (si no lo tienes ya instalado) el siguiente software:
  * [Eclipse](http://www.eclipse.org/downloads/)
  * [Subversion](http://subversion.tigris.org/project_packages.html)
  * [Subclipse](http://subclipse.tigris.org/faq.html)

## Obtener el código fuente de Protégé y compilarlo ##
Sigue cuidadosamente las instrucciones que figuran en la página de la wiki de Protégé para poder descargar el código fuente, compilarlo y ejecutar Protégé en Eclipse: http://www.cs.man.ac.uk/~iannonel/eclipseSetup/eclipseSetupMain.html.

## Descargar el código del plug-in ##
  1. Importamos el proyecto desde el repositorio: para ello, desde Eclipse escogemos File/Import
  1. En la ventana que nos aparece escogemos SVN Checkout from SVN, tal y como se indica en la siguiente imagen ![http://lh5.ggpht.com/_bLiTBqZwnZk/SlTDpbyDyhI/AAAAAAAAAPk/xfqeq6yuhhc/s800/Imagen%202.png](http://lh5.ggpht.com/_bLiTBqZwnZk/SlTDpbyDyhI/AAAAAAAAAPk/xfqeq6yuhhc/s800/Imagen%202.png)
  1. En la siguiente ventana escogemos crear un nuevo repositorio y pulsamos Next
  1. Introducimos la URL del repositorio http://violet-owl.googlecode.com/svn/trunk/ y pulsamos Next
  1. Escogemos la carpeta org.protege.editor.owl.example.tab  y pulsamos Finish ![http://lh5.ggpht.com/_bLiTBqZwnZk/SlTDmaV73UI/AAAAAAAAAPc/kSMbTzNh7u8/s800/Imagen%203.png](http://lh5.ggpht.com/_bLiTBqZwnZk/SlTDmaV73UI/AAAAAAAAAPc/kSMbTzNh7u8/s800/Imagen%203.png)

## Compilar y Ejecutar el Código ##
  1. Asegúrate de que los proyectos de Protégé y el plug-in están en el mismo Workspace.
  1. Comprueba que el build path del proyecto está bien configurado según lo que se explica en [este enlace](http://protegewiki.stanford.edu/index.php/CompileProtege4PluginInEclipseOneProject#Building_the_Plugin) y compila el plug-in
  1. Sigue las instrucciones de [este enlace](http://protegewiki.stanford.edu/index.php/CompileProtege4PluginInEclipseOneProject#Running_the_Plugin) para configurar la ejecución del plug-in
  1. Si nada ha ido mal, podrás ejecutar Protégé y el plug-in con él.

## Genera el plug-in ##
  1. Arrancamos el panel de configuración de herramientas externas y creamos una nueva configuración Ant Build
  1. En la pestaña Main escogemos el fichero build.xml, que contiene el script ant que genera el plugin, tal y como se indica en la siguiente imagen: ![http://lh3.ggpht.com/_bLiTBqZwnZk/SlTMSluU_BI/AAAAAAAAAQY/y8PmGPFEB4M/s800/Imagen%205.png](http://lh3.ggpht.com/_bLiTBqZwnZk/SlTMSluU_BI/AAAAAAAAAQY/y8PmGPFEB4M/s800/Imagen%205.png)
  1. Desde la pestaña Environment, creamos una variable de entorno llamada PROTEGE\_HOME, que nos dirá donde instalar el plug-in; asignamos su valor al directorio donde se encuentra Protégé (no tiene porque ser necesariamente el directorio donde se encuentra la versión compilada por nosotros de Protégé, también puede ser el directorio donde tengamos instalada una versión "binaria" de Protégé descargada directamente) ![http://lh5.ggpht.com/_bLiTBqZwnZk/SlTMT7fNmBI/AAAAAAAAAQc/Bd7AQfzWybk/s800/Imagen%204.png](http://lh5.ggpht.com/_bLiTBqZwnZk/SlTMT7fNmBI/AAAAAAAAAQc/Bd7AQfzWybk/s800/Imagen%204.png)
  1. En la pestaña Targets seleccionamos el Target Install.
  1. Aplicamos los cambios, ejecutamos y listo. El script compila el código, copia las librerías y recursos necesarios, genera el jar y finalmente lo copia en la carpeta apuntada por la variable de entorno PROTEGE\_HOME.
  1. Ejecuta Protégé y comprueba que el plug-in se ha instalado correctamente y funciona. Al seleccionar la pestaña deberías ver algo así: http://lh4.ggpht.com/_bLi