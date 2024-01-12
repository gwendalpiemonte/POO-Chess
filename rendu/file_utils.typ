#import "@preview/codelst:1.0.0": sourcecode


#let listing_entry = (path, title: "", lang: "java") => [
  #if title.len() > 0 [
    == #title
  ] else [
    == #path
  ]
  
  #sourcecode[#raw(read(path),lang: lang)]
]

#let listing = (listing: "./generated/listing.json", prefix: "./src/main/java/") => {
  let output = []
  let contents = json(listing)
  for entry in contents {
    let title = if entry.starts-with(prefix) {
      entry.slice(prefix.len())
    } else {
      entry
    }

    // With the title, we can then generate the listing

    output = output + listing_entry(entry, title: title)
  }

  output
}