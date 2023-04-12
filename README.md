# Aplicatie de gestionare a quest-urilor
## autor: Varga Adam

## Introducere
Aceasta aplicatie este prima versiune a unui sistem de gestionare a sarcinilor(quests) care permite utilizatorilor sa urmareasca si sa gestioneze diferite quest-uri, pentru a castiga token-uri sau badge-uri. Acest sistem ofera o vedere de ansamblu asupra tuturor quest-urilor în desfasurare si permite participantilor sa lucreze fie impreuna, fie individual pentru a le finaliza cu succes. Prin intermediul acestui sistem, cei care creeaza quest-urile(quest masters) pot atribui aceste "misiuni" altor utilizatori, pot monitoriza progresul misiunilor, pot aproba sau esua misiunile și pot primi notificări pentru actualizări și schimbări.

## Tehnologii utilizate
Pentru a implementa aceasta aplicatie, am realizat un serviciu web de tip REST API folosind urmatoarele tehnologii:
### Building blocks
- Java 17
- Spring Boot 3, cu dependintele
  1. spring-security si jsonwebtoken: Pentru implementarea securitatii serviciului folosind JWT
  2. spring-data-jpa: Pentru gestionarea si persistenta datelor intr-o baza de date
  3. spring-validation: Pentru validarea cererilor de tip HTTP
  4. spring-test: Pentru unit si integration testing
  5. jackson: Pentru a facilita gestionarea cererilor si raspunsurilor sub forma de JSON
- O baza de date relationala MySql (sau MySqlServer prentru versiunea de staging pe platforma Azure)

### Pentru dezvoltarea aplicatiei
- IntelliJ Idea
- Insomnia API 

## Utilizarea aplicatiei
In ciclul de viata al unui quest, exista doua tipuri de utilizatori, si anume questmaster-ul si quest user-ul.
- Quest master: este un utilizator al aplicatiei care creeaza quest-ul, in schimbul unui cost de token currency care va fi distribuit intre utilizatorii care participa. Motivatia quest master-ului de a creea aceste sarcini este recompensarea unui quest finalzat cu 200 de currency tokens + valoarea "investitiei" initiale pentru a creea quest-ul. Quest master-ul nu va primi recompensa in cazurile in care:
  - La momentul expirarii, quest master-ul nu a verificat toate quest-urile( exista quest-uri cu statusul IN_REVIEW) 
  - Utilizatorii selectati pentru realizarea quest-ului si-au anulat quest-urile.\
    </br>
- Quest user: este un utilizator care a fost desemnat de catre quest master pentru a realiza quest-ul. Daca acesta nu doreste sa participe, poate anula quest-ul, caz in care acesta nu il va mai putea completa, iar recompensa individuala va fi recalculata bazat pe numarul de utilizatori ramasi. In cazul in care utilizatorul a realizat quest-ul dupa descrierea acestuia, acesta ii va schimba statusul in IN_REVIEW, semnaland astfel quest master-ului sa verifice sarcina respectiva. In cazul in care quest measter-ul aproba quest-ul, utilizatorul va primi recompensa, iar statusul se va schimba in COMPLETED. In cazul de esuare a quest-ului sau expirare, statusul va fi schimbat in FAILED.
</br>
</br>
Exista doua modalitati de cuantificare a succesului unui utilizator:
- Prin rangul utilizatorului pe un leaderboard care plaseaza utilizatorii in functie de numarul curent de currency tokens.
- Prin badge-urile detinute de acestia, care pot fi de mai multe feluri. In implementarea curenta exista doua tipuri: badge-uri primite pentru atingerea unui numar fixat de currency tokens, sau badge-uri primite pentru atingerea unui anumit rank.
## Arhitectura
###User
Modulul User contine logica de business atribuita gestionarii untilizatorilor. 
</br>
####Endpoint-uri REST:
&nbsp;&nbsp;&nbsp;&nbsp;<b>GET</b> /users/current-user -> Date despre utilizatorul curent
</br>
</br>
&nbsp;&nbsp;&nbsp;&nbsp;<b>GET</b> /users/current-user/badges -> Date despre badge-urile utilizatorului curent
</br>
</br>
&nbsp;&nbsp;&nbsp;&nbsp;<b>GET</b> /users/ranking -> Date despre leaderboard cu utilizatori
###Quest
Modulul Quest contine logica de business atribuita gestionarii quest-urilor si userQuest-urilor.
</br>
####Endpoint-uri REST:
&nbsp;&nbsp;&nbsp;&nbsp;<b>GET</b> /quests/user-quests -> Date despre user quest-urile utilizatoruului curent
</br>
</br>
&nbsp;&nbsp;&nbsp;&nbsp;<b>POST</b> /quests/status-change -> Folosit pentru a schimba statusul unui user quest
</br>
</br>
&nbsp;&nbsp;&nbsp;&nbsp;<b>GET</b> /quests -> Date despre quest-urile pentru care utilizatorul curent este quest master
</br>
</br>
&nbsp;&nbsp;&nbsp;&nbsp;<b>POST</b> /quests/create -> Folosit pentru a creea un nou quest
###Badge
Modulul Badge contine logica de business atribuita gestionarii badge-urilor.
</br>
####Endpoint-uri REST:
&nbsp;&nbsp;&nbsp;&nbsp;<b>GET</b> /badges -> Date despre toate badge-urile
</br>
</br>
&nbsp;&nbsp;&nbsp;&nbsp;<b>POST</b> /badges/create -> Folosit pentru a creea un nou badge
</br>
</br>
###Functionalitati de scheduling
Pentru a avea rank-uri actualizate permanent, a fost folosit un Scheduled component care actualizeaza rank-urile utilizatorilor bazat pe un cron schedule.
Pentru a gestiona expirarea unui quest, la creeare se porneste un task scheduler setat pe data de expirare care va realiza functinalitatea de expirare

## Imbunatatiri viitoare
1. Prioritate ridicata
   - Realizarea unei aplicatii client care sa consume servicul web in ReactJs sau AngularJs
   - Adaugarea functionalitaii de digital proof: utilizatorii pot incarca fisiere care atesta finalizarea quest-ului, iar quest master-ul poate revizui fisierele trimise
   - Rezolvarea problemelor cu deployment-ul pe platforma azure
   - Marirea procentului de test coverage pentru testele unitare si de integrare
2. Prioritate medie
    - Corectarea functionalitatii de expirare deoarece exista posibilitatea ca utilizatorul sa isi schimbe quest-ul in ultimele momente inainte de expirare, astfel quest master-ul ne primind recompensa
   - Refactorizarea functinalitatii de badges
   - Extragerea functinalitatii de currency token intr-o clasa separata

#Link catre demo:https://drive.google.com/file/d/15-5jt2opJbanfg6yV59yzgp6knCF0cxC/view?usp=share_link
