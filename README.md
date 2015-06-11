# Italiano

### Compilare i sorgenti :

Per compilare i sorgenti è necessario creare un progetto che supporti JavaFX 2.2 (io utilizzo NetBeans) con JavaSE 7.
Mettere tutti i sorgenti nella cartella src.
Creare una cartella 'libs' con dentro i seguenti jar :

* httpclient : tutti i file jar [source](http://commons.apache.org/httpclient)

* xstream : xstream jar + xmlpull + xpp3_min [source](http://xstream.codehaus.net)

E aggiungere le librerie alle dipendenze del progetto. Aggiungere inoltre JUnit 4.10 per compilare gli unit test.

La distribuzione ufficiale utilizzerà icone di alta qualità.

Per compilare il progetto sono state inserite icone segnaposto e l'applicazione è progettata per utilizzare le icone e la grafica segnaposto in mancanza di quella ufficiale.

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
- [ ] Terminare il refactoring del pannello per la visualizzazione dei file
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

And add the libs to the project dependencies. Also add JUnit 4.10 for compiling unit tests.

Official distribution will use high quality icons. 

In order to compile the project free stub graphics has been put inside the project, 
and the application will use the fallback icons and graphics when the official distribution graphics is not available.

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
- [ ] Finish refactoring of file browse panel
- [ ] Implement auto-update (with installer o inside the application)
- [ ] Put the preview installers on the offical website
- [x] Create the installer



*- Marco Bagnaresi*
