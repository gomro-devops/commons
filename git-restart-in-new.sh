#!/usr/bin/env bash

echo "正在拉取最新代码..."
git fetch origin
echo "拉取最新 main 分支..."
git checkout -B main-bak origin/main
echo "创建无历史分支..."
git checkout --orphan latest_branch
git add -A
git commit -m "initial"
echo "本地初始化为 main..."
git branch -D main
git branch -m main
echo "强制更新远程 main..."
git push -f origin main
