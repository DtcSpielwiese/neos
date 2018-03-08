
### Es muss folgende per Download installiert werden:
* [InteliJ](https://www.jetbrains.com/idea/download/)
* [NodeJS](https://nodejs.org/en/download/)
* [GitShell](https://git-scm.com/downloads)
* [GitGui - SourceTree](https://www.sourcetreeapp.com)

### TAB vervollständigung im Terminal:
* Terminal öffnen
```bash
nano .inputrc
```
Quelle: http://osxdaily.com/2012/08/02/improve-tab-completion-in-mac-os-x-terminal/
* Paste in the following three rules on unique lines:  set completion-ignore-case on set show-all-if-ambiguous on TAB: menu-complete 
* Hit Control+O to save changes to .inputrc followed by control+X to quit
* Open a new Terminal window or tab, or type “login” to open a new session with the rules in effect
* Start typing a command, path, or something else and hit the Tab key to see the improvements firsthand

### Apstore
[XCODE Installieren](https://itunes.apple.com/de/app/xcode/id497799835?mt=12)


### Installation der Sourcen über NPM / Yarn
* Öffnen des Terminal
```bash
sudo npm install --global yarn
sudo yarn global add typescript
sudo yarn global add @angular/cli
sudo yarn global add ionic@latest
sudo yarn global add cordova
sudo yarn install
```

### Installieren einer App auf einem IOS-Testgerät
* ist nach folgender Anleitung sdurchzuführen: https://ionicframework.com/docs/intro/deploying/
