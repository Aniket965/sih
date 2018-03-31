var Cryptr = require('cryptr'),
    cryptr = new Cryptr('bhutJdaKufia');

    var encryptedString = cryptr.encrypt('https://not-so-awesome-project-45a2e.firebaseio.com')

console.log(encryptedString);