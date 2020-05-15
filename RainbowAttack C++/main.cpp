#include <iostream>
#include <vector>
#include <cstdarg>
#include <ctime>
#include "rainbow_table.hpp"
#include "sha256.h"

/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 */

/**
 * @brief generates random string of specific length
 * @param length the length of the string to generate
 * @return the randomly generated string
 */
std::string generate_random_string(size_t length)
{
    const char * char_map = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    const size_t char_map_len = strlen(char_map);
    auto generator = [&](){ return char_map[rand() % char_map_len]; };
    
    std::string result;
    result.reserve(length);
    generate_n(back_inserter(result), length, generator);
    
    return result;
}

/**
 * @brief generates random integer in range
 * @param min the minimum of the range
 * @param max the maximum of the range
 * @return the randomly generated integer
 */
int random_int_in_range(int min, int max) {
    return rand() % (max - min + 1) + min;
}

/**
 * @brief the entry point of the rainbow table software
 * @param argc the arguments count
 * @param argv the arguments values
 * @return the exit code
 */
int main(int argc, const char * argv[]) {
    
    constexpr char generate_rt_fag[] = "generate_rt";
    constexpr char lookup_rt_fag[] = "lookup_rt";
	
	// set the rand seed
	srand(time(NULL));
    
    if (argc > 1) {
        if (strcmp(argv[1], generate_rt_fag) == 0) {
            // ./rainbow_table generate_rt rt.txt 100 1000
            if (argc != 5) {
                std::cout << "Wrong arguments list" << std::endl;
                return 1;
            } else {
                std::string target_file = argv[2];
                unsigned long long max_size = std::stoull(argv[3]);
                unsigned reduction_count = std::stoi(argv[4]);
                
                rainbow_table rt(
                                 [](std::string input) -> std::string { return sha256(input); },
                                 []() -> std::string { return generate_random_string(random_int_in_range(5, 8)); },
                                 5,
                                 8,
                                 reduction_count);
                
                rt.generate(target_file, max_size);
                std::cout << "rt generated to " << target_file << std::endl;
            }
        } else if(strcmp(argv[1], lookup_rt_fag) == 0) {
            // ./rainbow_table lookup_rt rt.txt pairs.txt 1000
            if (argc != 5) {
                std::cout << "Wrong arguments list" << std::endl;
                return 1;
            } else {
                std::string src_rt_file = argv[2];
                std::string src_pairs_file = argv[3];
                unsigned reduction_count = std::stoi(argv[4]);
                
                rainbow_table rt(
                                 [](std::string input) -> std::string { return sha256(input); },
                                 []() -> std::string { return generate_random_string(random_int_in_range(5, 8)); },
                                 5,
                                 8,
                                 reduction_count);
                rt.find_multiple_from_file(src_rt_file, src_pairs_file);
            }
        }
    }
    return 0;
}

