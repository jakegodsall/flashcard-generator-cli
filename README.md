# Flashcard Generator CLI

This project represents the `flashcard-generator-cli` command-line interface application that allows users to generate language learning flashcards using the `flashcard-generator-core` library. 

This tool leverages the GPT API to create example sentences with provided words in various languages, aiding in language learning.
The generated flashcards can be exported to different formats, such as CSV and JSON.

## Features
- Command-line interface for easy interaction.
- Generate flashcards using a specified word or set of words in various languages.
- Support for different language-specific options, such as choosing grammatical tense or including stress symbols.
- Export generated flashcards to multiple file formats (CSV, JSON).
- Interactive, batch processing, and file-based input modes.

## Requirements
- Java 22 or higher
- Maven
- An OpenAI API key
- The flashcard-generator-core library (included as a dependency)

## Setup

## Setup
1. Clone the repository:

   ```shell
   git clone https://github.com/yourusername/flashcard-generator-cli.git
   cd flashcard-generator-cli
   ```

2. Build the project:

   ```shell
   mvn clean package
   ```

3. Run the application

   After building the project, the JAR file located in the `target/` directory can be included as a dependency in other applications that wish to utilize the flashcard generation capabilities of this library.

   ```shell
   java -jar target/flashcard-generator-cli-1.0-with-dependencies.jar
   ```


## API Key Management

The Flashcard Generator CLI requires an API key to access the language processing services that power the flashcard generation functionality.

### Storage
When you first run the Flashcard Generator CLI application, you will be prompted to enter your API key. 

This key will be stored in the `api_config.json` file within the `.flashcard-generator` directory located in your home directory.

For subsequent uses, the application will automatically read the API key from this file. You won’t need to enter the API key again unless you choose to modify it.

### Modifying the API Key

If you need to change or update your API key, you can do so by either modifying it directly in the file or deleting the file.

Once you delete this file and run the application again, you will be prompted to add the API key.

##### Important Note
It is important to keep your OpenAI API key secure and not share it with others. Usage charges may apply depending on the OpenAI services you use through the application.

## Application Modes

The Flashcard Generator CLI offers flexible modes for both inputting data and exporting the generated flashcards. This flexibility allows you to choose the mode that best fits your workflow and preferences.

### Input Modes

1. **Interactive Mode**: In Interactive Mode, the application prompts you to enter words or phrases one at a time. After you input each word or phrase, the application generates a flashcard using the flashcard-generator-core. Each flashcard is printed to the console and stored in memory for exporting later.
2. **Comma Separated String Mode**: In this mode, you provide a single string containing multiple words or phrases separated by commas. The application processes each item in the string and generates flashcards for each one. Each flashcard is stored in memory for exporting later.
3. **Plain Text File Mode**: In Plain Text File Mode, you can input data by providing a plain text file where each line contains a word or phrase. The application reads the file and generates flashcards for each line. Each flashcard is stored in memory for exporting later.

### Output Modes

1. **CSV Mode**: Each flashcard is exported as a separate line in a CSV file where fields are separated by commas.
2. **JSON Mode**: Flashcards are exported as a JSON file.

## Language Configuration

The Flashcard Generator CLI allows for language-specific configurations to tailor the flashcard generation process to the nuances of different languages.
Language-specific features are configured via the `language_config.json` file located in the `src/main/resources` directory. This file includes options for stress symbols, formality, gender, dialects, politeness levels, and conjugation tenses.

Example `language_config.json`:

```json
{
"ru": {
   "name": "Russian",
   "supports_stress": true,
   "tenses": ["PAST", "PRESENT", "FUTURE"],
   "genders": ["MASCULINE", "FEMININE", "NEUTER"]
  },
"pl": {
   "name": "Polish",
   "supports_stress": false,
   "tenses": ["PAST", "PRESENT", "FUTURE"],
   "genders": ["MASCULINE", "FEMININE", "NEUTER"]
},
"es": {
   "name": "Spanish",
   "supports_stress": false,
   "tenses": ["PAST", "PRESENT", "FUTURE"],
   "genders": ["MASCULINE", "FEMININE"]
  }
}
```

The choice of languages and the specific features that can be configured are heavily dependent on the capabilities of the underlying language model that the application uses. The language model's ability to accurately generate content in different languages and handle linguistic nuances like stress, gender, and tense is a key factor in determining how well the application can support each language.

## Future Plans

Future enhancements for the Flashcard Generator CLI include:

- Additional input and output formats.
- Improved error handling and user feedback.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or new features.

## License
This project is licensed under the MIT License.

