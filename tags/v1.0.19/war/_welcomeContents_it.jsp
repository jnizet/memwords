<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<div id="resume">
  <h2>Che cosa è MemWords</h2>
  <p>MemWords  è un gestore di password online gratuito.</p>
  <p>Salva le tue password in modalità sicura, proteggendole con un unica password principale.</p>
  <p>Da ovunque puoi loggarti su MemWords utilizzando la tua password principale, e ritrovare tutte le pasword che avevi precedentemente salvato.</p>
  <p style="font-size: x-large;"><stripes:link beanclass="com.googlecode.memwords.web.ScreenshotsActionBean">Dai un'occhiata agli screenshots!</stripes:link></p>
</div>
<div id="features">
  <h2>Caratteristiche</h2>
  <ul>
    <li>Completamente gratuito, e libero.</li>
    <li>Crea tutti gli account che vuoi, e aggiungi quante schede vuoi a ogni account.</li>
    <li>Connessione sicura tramite SSL.</li>
    <li>Tutti i dati sensibili salvati su database sono criptati.</li>
    <li>Le icone dei tuoi siti web vengono trovate e recuperate in automatico.</li>
    <li>Generatore casuale di password, con possibilità di personalizzazioni.</li>
    <li>Tradotto in varie lingue (al momento sono supportati Inglese, Francese e Italiano).</li>
    <li>L'utilizzo di JavaScript e AJAX rendono migliore la navigabilità del sito, ma anche senza JavaScript funziona tutto.</li>
  </ul>
</div>
<div id="faq">
  <h2>FAQ</h2>
  <div class="question">
    E' sicuro?
  </div>
  <div class="answer">
     Si. La comunicazione fra il tuo computer e il server dove vengono salvate le password è protetta tramite SSL.
  </div>
  <div class="question">
    E che mi dite dell'amministratore di MemWords? Se guarda i dati su database, trova le mie password, no?
  </div>
  <div class="answer">
    No. La maggior parte delle informazioni salvate è criptata: le password, ma anche le utenze, le note, le URLs, le icone, ecc.
  </div>
  <div class="question">
    Ok, è criptata, ma l'amministratore deve conoscere la chiave, altrimenti non si potrebbe decriptare, no?
  </div>
  <div class="answer">
    No. La chiave usata per criptare i <em>tuoi</em> dati è protetta dalla <em>tua</em> password principale, che conosci solo tu.
    <br/>
    <a href="http://code.google.com/p/memwords/wiki/Security" class="external">Maggiori informazioni sulla sicurezza in MemWords</a>
  </div>
  <div class="question">
    Che algoritmo viene usato per proteggere le mie passwords?
  </div>
  <div class="answer">
    MemWords usa principalmente questi due algoritmi per criptare: AES-128 e SHA-256.
  </div>
  <div class="question">
    Perchè non usa AES-256?
  </div>
  <div class="answer">
    Prima ragione: MemWords è un progetto creato per hobby e ospitato gratuitamente su
    <a href="http://code.google.com/appengine/">Google 
    App Engine</a>. E Google App Engine non supporta AES-256.<br/>
    Seconda ragione: AES-128 è abbastanza sicuro. L'anello debole sarebbe comunque la tua password principale. Se proprio sei preoccupato che si scoprano le tue password, 
    non salvarle da nessuna parte eccetto che nel tuo cervello.
  </div>
  <div class="question">
    E se la mia password principale fosse compromessa?
  </div>
  <div class="answer">
     Peccato. Puoi comunque cambiarla, o distruggere il tuo account. Ma la sicurezza del tuo account
    dipende dalla forza della password principale. Scegline una forte, e  non dirla a nessuno.
  </div>
  <div class="question">
    E se mi dimentico la password principale?
  </div>
  <div class="answer">
    Peccato. MemWords è progettato per impedire a chiunque, anche a chi ha accesso alla banca dati, di trovare una password principale.
    Il tuo account continuerà a esistere in eterno, ma nessuno sarà mai più in grado di accedervi.
  </div>
  <div class="question">
    Mi garantite che non perderete mai le mie informazioni?
  </div>
  <div class="answer">
    No. MemWords è stato creato per hobby. Io metto a disposizione l'applicazione, tu puoi vedere il codice sorgente, ma usala a tuo rischio e pericolo.
  </div>
</div>