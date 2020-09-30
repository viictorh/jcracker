# JCracker

JCracker is a Java application used to crack JSON Web Tokens. It's based on  [jwt-cracker](https://github.com/lmammino/jwt-cracker), but using Java and with the possibility of wordlist as a parameter.

# Requirements
* Java >= 8

# Usage
java -jar jcracker.jar -t <jwtToken>

**Options**
| Parameter | Definition | Example | Default Value |
| ------ | ------ | ----- | ---- |
| a | Letters or caracters to make the combinations | -a abcdefgh123 | abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 |
| f | File path to wordlist | -f C:\teste\wordlist\wordlist.txt | NULL
| m | This parameter is used with alphabet only. With it, you specifies the max length of the word. Eg, if the m = 3, the max attempt of alphabet (a-z) will be zzz | -m 14 | 14|
| v | Whether should show more information of the execution | -v | false

