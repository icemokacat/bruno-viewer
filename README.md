# Bruno API Client Docs Viewer

<!--배지-->
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

무료로 사용할 수 있는 [bruno api client](https://www.usebruno.com/) 에서 docs 의 마크다운을 볼 수 있는 프로그램입니다.

<!--프로젝트 버튼-->
[![Report bug][report-bug-shield]][report-bug-url]

# Table of Contents

- [About the Project](#about-the-project)
  - [Features](#features)
  - [Technologies](#technologies)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [Usage](#usage)
- [Acknowledgement](#acknowledgement)
- [License](#license)

# About the Project

- 브루노 Api Client 에서 제공한는 Docs 만 별도로 관리하기 위해 만들어진 프로젝트입니다.
- 문서만 별도로 관리하면서, Front-end 에서 custom 하게 디자인하여 보기좋게 공유하기 위해 만들었습니다.
- .bru 파일을 읽어서 docs {} 부분의 markdown 을 읽어서 보여줍니다.

## Features

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)

## Technologies

- [Gradle](https://gradle.org/) 8.8
- [SpringBoot](https://spring.io/projects/spring-boot) 3.3.3
- [Thymeleaf](https://www.thymeleaf.org/) 3.3.3
- [commonmark](https://commonmark.org/) 0.22.0

# Getting Started

## Prerequisites

- Java 17.0.10+11-LTS-240

## Installation
Repository 클론
```bash
git clone https://github.com/icemokacat/bruno-viewer.git
```


## Configuration

- `application-dev.yml 을 참고하여 application-local.yml 을 만들어서 사용하세요.`

```yml
server:
  port: 7001

bruno:
  # .bru 파일이 있는 Collection의 폴더 경로 혹은 Collection 이 모여 있는 루트 path 를 설정하세요
  root-path: C:\work\bruno-project\

mdreader:
  # markdown 파일을 읽을 때 사용할 thread pool size 를 설정하세요
  poolsize: 10
```

# Usage

# Acknowledgement

- [Markdown to html (commonmark)](https://mvnrepository.com/artifact/org.commonmark/commonmark)
- [Spring으로 markdown view 만들기](https://devocean.sk.com/blog/techBoardDetail.do?ID=163499)
- [Github Markdown CSS](https://github.com/sindresorhus/github-markdown-css) by [sindresorhus](https://github.com/sindresorhus)

# License
 Apache License 2.0

 라이센스에 대한 정보는 [`LICENSE`](/LICENSE)에 있습니다.

<!-- 링크 -->

[report-bug-shield]: https://img.shields.io/badge/-%F0%9F%90%9E%20report%20bug-F5A9A9?style=for-the-badge
[report-bug-url]: https://github.com/icemokacat/bruno-viewer/issues