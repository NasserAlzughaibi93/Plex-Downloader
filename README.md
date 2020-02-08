# Plex-Downloader
(WIP) Web interface to to browse and download media from a Plex server you have access to.

---
|  Stable         | Develop   | Docker Hub (Master)
|----------|:---------------------------:|:----------------------------:|
| [![Build status](https://circleci.com/gh/Halahala1993/Plex-Downloader/tree/master.svg?style=svg)](https://circleci.com/gh/Halahala1993/Plex-Downloader/tree/master) | [![CircleCI](https://circleci.com/gh/Halahala1993/Plex-Downloader/tree/develop.svg?style=svg)](https://circleci.com/gh/Halahala1993/Plex-Downloader/tree/develop) | [![Docker Hub](https://dockeri.co/image/halahala/plex-downloader)](https://hub.docker.com/repository/docker/halahala/plex-downloader)

# Usage
- Sign in using your plex account.
- Select which server you want to view.
- Browse or search for media you wish to download (currently limited to single movie and/or episode per download).
- To view swagger doc: `localhost:8080/swagger-ui.html`
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