#!/bin/bash

set -e -u

if [ "$TRAVIS_REPO_SLUG" == "natrolite/natrolite" ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk8" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  mkdir -v -p $HOME/javadoc
  cp -v -R build/docs/aggregated/ $HOME/javadoc

  cd $HOME
  git clone -q -b gh-pages https://${GH_TOKEN}@github.com/natrolite/jd.natrolite.org gh-pages > /dev/null
  cd gh-pages

  git config --global user.name "travis-ci"
  git config --global user.email "travis@travis-ci.org"

  cp -Rf $HOME/javadoc/. .
  git add -f .

  git commit -m "Deploy $(date)" > /dev/null
  git push -fq origin gh-pages > /dev/null

  echo "Deployed javadoc to gh-pages"
fi