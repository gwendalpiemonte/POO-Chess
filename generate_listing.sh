find ./src/main/java/engine -type f -print | jq -R -s 'split("\n") | map(select(length > 0)) | map("." + .)' \
    > ./rendu/generated/listing.json