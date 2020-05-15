//Author : Meihdi El Amouri and Cédric Thonus
const text = document.querySelector('.typing-text');

// make a words array
const words = [
    "an",
  "a branding",
  "a marketing",
  "an escort",
  "a communication",
  "an advertising",
  "a digital"
];

// start typing effect
setTyper(text, words);

function setTyper(element, words) {

  const LETTER_TYPE_DELAY = 65;
  const WORD_STAY_DELAY = 1700;

  const DIRECTION_FORWARDS = 0;
  const DIRECTION_BACKWARDS = 1;

  var direction = DIRECTION_FORWARDS;
  var wordIndex = 0;
  var letterIndex = 0;

  var wordTypeInterval;

  startTyping();

  function startTyping() {
    wordTypeInterval = setInterval(typeLetter, LETTER_TYPE_DELAY);
  }

  function typeLetter() {
    const word = words[wordIndex];

    if (direction == DIRECTION_FORWARDS) {
      letterIndex++;

      if (letterIndex == word.length) {
        direction = DIRECTION_BACKWARDS;
        clearInterval(wordTypeInterval);
        setTimeout(startTyping, WORD_STAY_DELAY);
      }

    } else if (direction == DIRECTION_BACKWARDS) {
      letterIndex--;

      if (letterIndex == 0) {
        nextWord();
      }
    }

    const textToType = word.substring(0, letterIndex);

    element.textContent = textToType;
  }

  function nextWord() {

    letterIndex = 0;
    direction = DIRECTION_FORWARDS;
    wordIndex++;

    if (wordIndex == words.length) {
      wordIndex = 0;
    }

  }
}
function swapStyle(sheet){
    
    var text = document.getElementById('pagestyle').getAttribute('href');
    console.log(text);
    if(text == 'style/styles.css'){
        document.getElementById('pagestyle').setAttribute('href',"style/"+sheet);
        document.getElementById('imageLogo').setAttribute('src','image/White.png');
        document.getElementById('imageMobile').setAttribute('src','image/White.png');
    }else{
         document.getElementById('imageLogo').setAttribute('src','image/Logo%20blackchick.png');
         document.getElementById('imageMobile').setAttribute('src','image/Logo%20blackchick.png');
        document.getElementById('pagestyle').setAttribute('href','style/styles.css');
        
    }
     }

function toggleText(button_id)  {
   var text = document.getElementById(button_id).firstChild;
   text.data = text.data == "BLACK" ? "WHITE" : "BLACK";
}
