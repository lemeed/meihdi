#  rainbow attack

### authors
- Michaluk David <49762@etu.he2b.be>
- El Amouri Meihdi <49262@etu.he2b.be>
- Azoud Ismael <42394@etu.he2b.be>
- Rossitto Nicolas <49282@etu.he2b.be>

### build the software
```
make all
```

### run the software

### rainbow table generation
```
./rainbow_table generate_rt [destination_file] [max_rainbow_table_size_in_bytes] [reduction_count]
# Example
./rainbow_table generate_rt /some/absolute/path/rt.txt 10000 100
```

### rainbow table lookup
```
./rainbow_table lookup_rt [source_rainbow_table_file] [source_pairs_file] [reduction_count]
# Example
./rainbow_table lookup_rt /some/absolute/path/rt.txt /some/absolute/path/pairs.txt 100
```
A pairs example file is available under the name `pairs_example.txt`

### notes
You can set the `RT_DEBUG=1` environment flag.  When generatting rainbow table, this will additionnaly print to `stdout` the hash chains.
This can be useful to test the program with small-sized rainbow table.

