#!/usr/bin/env bash

# requires curl & jq

# upstreamCommit <baseHash>
# param: bashHash - the commit hash to use for comparing commits (baseHash...HEAD)

(
set -e
PS1="$"

purpur=$(curl -H "Accept: application/vnd.github.v3+json" https://api.github.com/repos/pufferfish-gg/Pufferfish/compare/"$1"...HEAD | jq -r '.commits[] | "pufferfish-gg/Pufferfish@\(.sha[:7]) \(.commit.message | split("\r\n")[0] | split("\n")[0])"')

updated=""
logsuffix=""
if [ -n "$purpur" ]; then
    logsuffix="$logsuffix\n\nPufferfish Changes:\n$purpur"
    updated="Pufferfish"
fi
disclaimer="Upstream has released updates that appear to apply and compile correctly.\nThis update has not been tested by SapphireMC and as with ANY update, please do your own testing"

log="${UP_LOG_PREFIX}Updated Upstream ($updated)\n\n${disclaimer}${logsuffix}"

echo -e "$log" | git commit -F -

) || exit 1
