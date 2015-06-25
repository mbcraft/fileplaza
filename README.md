# Italiano

### Compilare i sorgenti :

Per compilare i sorgenti è necessario creare un progetto che supporti JavaFX 2.2 (io utilizzo NetBeans) con JavaSE 7.
Mettere tutti i sorgenti nella cartella src.
Creare una cartella 'libs' con dentro i seguenti jar :

* httpclient : tutti i file jar [source](http://commons.apache.org/httpclient)

* xstream : xstream jar + xmlpull + xpp3_min [source](http://xstream.codehaus.net)

* jing : tutti i jar necessari (isorelax.jar, jing.jar, saxon.jar, xercesImpl.jar, xml-apis.jar)

E aggiungere le librerie alle dipendenze del progetto. Aggiungere inoltre JUnit 4.10 per compilare gli unit test.

La distribuzione ufficiale utilizzerà icone di alta qualità.

Per compilare il progetto sono state inserite icone segnaposto e l'applicazione è progettata per utilizzare le icone e la grafica segnaposto in mancanza di quella ufficiale.

Al momento utilizzo le seguenti librerie esterne (elenco esteso) :

- commons-codec-1.6.jar
- httpclient- cache-4.3.6.jar
- xmlpull-1.1.3.1.jar
- commons-logging-1.1.3.jar
- httpcore-4.3.3.jar
- xpp3_min-1.1.4c.jar
- fluent-hc-4.3.6.jar
- httpmime-4.3.6.jar
- xstream-1.4.7.jar
- httpclient-4.3.6.jar
- isorelax.jar
- jing.jar
- saxon.jar
- xercesImpl.jar
- xml-apis.jar
- junit-4.10.jar (solo per unit testing)

Inoltre è necessario che nel classpath sia presente anche jfxrt.jar, ovvero le librerie per usare JavaFX.

Quindi ad esempio, assumento che questi file siano in una cartella di nome 'lib' e che FilePlaza.jar sia nella cartella corrente, con all'interno tutti
i sorgenti compilati, è possibile lanciare il progetto da linea di comando con :

`
java -cp lib/*:/opt/java/64/jdk1.7.0_45/jre/lib/jfxrt.jar:FilePlaza.jar it.mbcraft.fileplaza.Main
`

Dove '/opt/java/64/jdk1.7.0_45/' è il percorso di installazione del jdk.

Al momento inoltre è necessario che una cartella 'data' sia presente nella directory corrente, con all'interno varie sottocartelle con alcuni file di configurazione xml. Forse in futuro migliorerò l'applicazione e rimuoverò questo requisito.


### Funzionalità :

* Installer multi-piattaforma con grafica curata
* Grafica segnaposto per consentire l'utilizzo dell'applicazione coi sorgenti di github
* Window manager innovativo a 4 pulsanti
* Editor del dizionario dei termini singulari/plurali utilizzati dal motore interno
* Finestra per bug report, opinioni e suggerimenti
* Aggiungi una descrizione ai file
* Associa una preview immagine personalizzata ai file ritagliando uno screenshot
* Mostra le informazioni del file (permessi, dimensioni, ecc.)

### Roadmap (ordine casuale) :

- [x] Progettare la nuova gestione finestra
- [x] Implementare la nuova gestione finestra
- [ ] Rifinire la nuova gestione finestra
- [x] Terminare il refactoring del pannello per la visualizzazione dei file
- [ ] Implementare l'auto-update dell'applicazione (con l'installer o interno all'applicazione)
- [ ] Rendere disponibili nel sito ufficiale gli installer delle versioni preview
- [x] Creazione dell'installer



*- Marco Bagnaresi*

# English

### Compiling the sources :

In order to compile the sources you need to create a JavaFX 2.2 project (i
use NetBeans) with JavaSE 7. Put all the sources in the src directory.
Create a 'libs' directory with the following
jars inside :

* httpclient : all jar files [source](http://commons.apache.org/httpclient)

* xstream : xstream jar + xmlpull + xpp3_min [source](http://xstream.codehaus.net)

* jing : all the required jars (isorelax.jar, jing.jar, saxon.jar, xercesImpl.jar, xml-apis.jar)

And add the libs to the project dependencies. Also add JUnit 4.10 for compiling unit tests.

Official distribution will use high quality icons. 

In order to compile the project free stub graphics has been put inside the project, 
and the application will use the fallback icons and graphics when the official distribution graphics is not available.

Actually i use the following external libraries (extended list) :

- commons-codec-1.6.jar
- httpclient- cache-4.3.6.jar
- xmlpull-1.1.3.1.jar
- commons-logging-1.1.3.jar
- httpcore-4.3.3.jar
- xpp3_min-1.1.4c.jar
- fluent-hc-4.3.6.jar
- httpmime-4.3.6.jar
- xstream-1.4.7.jar
- httpclient-4.3.6.jar
- isorelax.jar
- jing.jar
- saxon.jar
- xercesImpl.jar
- xml-apis.jar
- junit-4.10.jar (unit tests only)

Moreover it's necessary to put jfxrt.jar in the classpath, the JavaFX runtime library.

So for example, if these jars are in a 'lib' folder and FilePlaza.jar is in the current folder, with all the compiled sources inside, it's possibile to launch the application writing the following from the command line :

`
java -cp lib/*:/opt/java/64/jdk1.7.0_45/jre/lib/jfxrt.jar:FilePlaza.jar it.mbcraft.fileplaza.Main
`

Where '/opt/java/64/jdk1.7.0_45/' is the path of jdk installation.

Actually it is also necessary that a data folder is put inside the current directory, containing various subfolder with xml configuration files. Maybe in the future i'll improve the software removing this requirement.

### Features :

* Multi platform installer/uninstaller with nice graphics
* Fallback graphics for enabling application launch from github sources
* Innovative 4 button window manager
* Dictionary editor for singular/plural terms used by internal engine
* Bug report, Review and Feedback dialogs
* Add description to files
* Add custom preview to files
* Show file informations (permission, size, etc.)

### Roadmap (random order) :

- [x] Create design of the new window manager
- [x] Develop the new window manager
- [ ] Improve graphics of the new window manager
- [x] Finish refactoring of file browse panel
- [ ] Implement auto-update (with installer o inside the application)
- [ ] Put the preview installers on the offical website
- [x] Create the installer



*- Marco Bagnaresi*
