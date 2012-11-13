#!/bin/sh
#config your default name when you commit your code
git config --global user.name "yauta"
#config your default email
git config --global user.email "caipengy@mail3.sysu.edu.cn"
# Set git to use the credential memory cache
git config --global credential.helper cache 'cache --timeout=3600'
