name := "Labo-Chess-Rendu.pdf"

compile: generate-listings
	typst compile ./main.typ $(name) --root ..
generate-listings:
	find ../src/main/java/engine -type f -print | \
		jq -R -s 'split("\n") | map(select(length > 0))' > ./generated/listing.json
