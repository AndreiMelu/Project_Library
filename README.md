# LIBRARY MANAGEMENT

## Scopul aplicatiei:
Aceasta aplicatie reprezinta o biblioteca online. Am ales aceasta aplicatie deoarece,
spre deosebire de alte aplicatii, aceasta foloseste pe langa o __interfata web__
si o __baza de date__. Aceasta contine obiecte precum: carti, reviste, CD-uri, 
DVD-uri etc si are __doua tipuri de utilizatori__: administratori si bibliotecari.
 
Functionalitatea aplicatiei va fi explicata in cele ce urmeaza.

## Reprezentarea datelor:

Proiectul este organizat in __doua pachete__, partea care opereaza cu 
baza de date si cea de web.

- In pachetul __db__, avem alte 3 pachete: dao, ddl si dto, pentru o organizare
  mai buna. 
  - In __ddl__ (Data Definition Language) se manipuleaza tabelele,
  - In __dao__ (Data Access Object) se implementeaza operatiile CRUD pentru manipularea datelor
  - In __dto__ se gasesc toate implementarile claselor folosite in acest proiect.

- In pachetul __web__ avem interfetele pentru cele 2 tipuri de utilizatori 
si pagina de login. Pentru interfata de login si cea de administratori s-a folosit 
__Apache Velocity__. Scopul principal este acela de a separa partea de design 
de partea de cod. Cu alte cuvinte, Velocity permite ca designerii si programatorii 
sa isi faca treaba cu o interactiune minima intre ei. 

Aceasta biblioteca se bazeaza pe o __baza de date__. Aceasta contine patru tabele
(USER, ITEMS, HISTORY, ROLE). Tabelele au fost facute si testate manual in DBeaver.
Legatura dintre utilizator si obiect se face pe baza tabelului History, 
care contine atat id-ul utilizatorului cat si pe cel al obiectului.


## Functionalitate aplicatie:

- __Administratorii__ au drept de acces/manipulare doar asupra utilizatorilor. 
  - pot defini utilizatori noi(administratori/bibliotecari)
  - pot vizualiza un tabel cu toti utilizatorii din sistem la momentul respectiv, 
  - pot actualiza unele detalii ale utilizatorilor existenti in sistem
  - pot sterge utilizatori, mai putin ultimul administrator aflat in sistem.

- __Bibliotecarii__ au drept de acces/manipulare a obiectelor din biblioteca online.
  - Utilizatorii vizualizeaza un tabel cu obiectele disponibile din librarie si 
    le pot imprumuta. La imprumutarea unui obiect din librarie, acesta dispare 
    din lista de obiecte disponibile si nu mai poate fi imprumutat de catre alt utilizator.
  - Se pastreaza un __istoric__ al tuturor impumutarilor, utilizatorul poate alege 
    daca un element imprumutat a fost distrus, pierdut, sau daca il inapoiaza. 
  - Adaugarea de noi obiecte in librarie se poate face de catre utilizatori, 
    fiind necesar macar numele obiectului si tipul acestuia.
 
________________________________________________________________________________
