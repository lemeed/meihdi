#ifndef rainbow_table_hpp
#define rainbow_table_hpp

#include <iostream>
#include <fstream>
#include <string>

/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 *
 * @brief A rainbow table utility
 * This class helps to create a rainbow table based on provided generator
 * and helps to search for reverse hash value from stored table
 */
class rainbow_table {
private:
    /**
     * @brief the hasher function takes a input string and returns the calculated hash
     */
    const std::function<std::string (std::string)> _hasher;
    
    /**
     * @brief the generator function is used as a source of passwords for the rainbow table generation
     */
    const std::function<std::string()> _generator;
    
    /**
     * @brief the count of iteration of reduction_count execution
     */
    const unsigned _reduction_count;
    const unsigned _min_password_size;
    const unsigned _max_password_size;
    
    /**
     * @brief execute n times the reduction function on given hash, where n is the given reduction count
     */
    std::string deep_reduce(std::string input_hash);
    
    /**
     * @brief search in hash chain (starting with initial password) for matched hash
     */
    std::unique_ptr<std::string> get_match_from_chain(std::string start_password, std::string lookup_hash);
public:
    /**
     * @brief a rainbow table constructor
     */
    rainbow_table(
                  std::function<std::string (std::string)> hasher,
                  std::function<std::string()> generator,
                  unsigned min_password_size = 5,
                  unsigned max_password_size = 8,
                  unsigned reduction_count = 50000) :
    _hasher(hasher),
    _generator(generator),
    _min_password_size(min_password_size),
    _max_password_size(max_password_size),
    _reduction_count(reduction_count) {}
    
    /**
     * @brief generate a rainbow table with limited size
     * @param target_file_path the path to the file where the rainbow table should be written
     * @param max_file_size the maximum file size of the rainbow table in bytes (defaults to 100MB)
     * @param open_mode the open mode for the file (defaults to truncate mode)
     */
    void generate(std::string target_file_path, unsigned long long max_file_size = 100'000'000ull, std::ios::openmode open_mode = std::ios::trunc);
    
    /**
     * @brief reduction function
     * @param reduce_index reduction iteration index
     * @param input_hash the hash to reduce
     * @param result_len the resulting reduced hash length
     * @return the reduced hash
     */
    static std::string reduce(unsigned reduce_index, std::string input_hash, unsigned result_len);
    
    /**
     * @brief search for hash reverse value from given rainbow table
     * @param src_file_path the source file holding the rainbow table
     * @param input_hash the input hash to search
     * @return pointer to reverse value if found, nullptr otherwise
     */
    std::unique_ptr<std::string> find(std::string src_file_path, std::string input_hash);
    
    /**
     * @brief search for multiple hashes reverse values from given rainbow table
     * @param src_rt_file_path the source file holding the rainbow table
     * @param src_hash_pairs_file_path the source file holding the (login, password_hash) pairs
     */
    void find_multiple_from_file(std::string src_rt_file_path, std::string src_hash_pairs_file_path);
};


#endif /* rainbow_table_hpp */
