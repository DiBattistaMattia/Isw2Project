#Deliverable 1
##Progetto: DAFFODIL
##Ticket: Fixed Bugs
##Comandi eseguiti
###Step 1
Nel codice fornito a lezione (GetBugtIDs) ho aggiunto una funzione (writeJsonFile) per scrivere ogni json object da Jira, in un nuovo file di tipo json. Il file contiene solo i campi "fields".
###Step 2
Una volta creato il file json, l'ho importato in google sheet. Per fare ciò ho dovuto aggiungere una funzione* (visibile in "strumenti" -> "editor di script"). Da notare che il file json per essere importato in google sheet, deve trovarsi in una cartella remota, per questo motivo l'ho inserito su GitHub e ho passato la URL come input della funzione in google sheet.
###Step 3
Ho creato i valori da riportare sull'asse delle ascisse (mese-anno) con un apposita funzione, e i corrispondenti valori sull'asse delle ordinate (fixed bugs). Per il conteggio ho usato la funzione =COUNTIF().
###Step 4
Ho prodotto il grafico, con i tre asintoti (mean, upper limit, lower limit).

##Osservazioni sul grafico
Il grafico rappresenta per ogni mese-anno, a partire dal 03-2012, il numero di ticket (di tipo Bug) chiusi per quella data. Sono inoltre rappresentate: la media pari a circa 9, l'upper limit di circa 33 ed il lower limit di -15.
Le formule per calcolare l'upper limit e il lower limit, sono rispettivamente:

- UPPER LIMIT = MEAN + 3 * STDV;
- LOWER LIMIT = MEAN - 3 * STDV; 

Con STDV indichiamo la deviazione standard.
Come già detto il lower limit ha un valore negativo, ovviamente questo non ha senso, è quindi da intendersi pari a zero. Non risultano esserci inoltre periodi troppo lunghi nei quali non si siano effettuati commit.

*scaricabile da: https://gist.githubusercontent.com/paulgambill/cacd19da95a1421d3164/raw/047b04a1c321b697533adad5828e6df8748b5e54/import_json_appsscript.js