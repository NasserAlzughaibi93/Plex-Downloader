# Plex-Downloader
(WIP) Web interface to to browse and download media from a Plex server you have access to.

# Usage
- Sign in using your plex account.
- Select which server you want to view.
- Browse or search for media you wish to download (currently limited to single movie and/or episode per download).

# Developing
- Install latest version of Node.js and at least Java 8.
- Import gradle modules with your favorite IDE.
- Run `npm install` in plex-downloader-ui
- Run `./gradle build` in parent directory

# TODO
- [ ] Admin controls 
  * [ ] limit to certain libraries
  * [ ] Download limits per user
- [ ] Work on ugly UI
- [x] Docker support
- [ ] CI/CD Pipeline
- [ ] Angular tests
- [ ] Batch downloading series