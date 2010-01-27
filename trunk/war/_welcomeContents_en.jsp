<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<div id="resume">
  <h2>What is MemWords</h2>
  <p>MemWords is a free online password manager.</p>
  <p>It stores your passwords in a secure way, protected by a single master password.</p>
  <p>Anywhere you are, log in to MemWords using your master password, and find all the passwords you have previously stored.</p>
  <p style="font-size: x-large;"><stripes:link beanclass="com.googlecode.memwords.web.ScreenshotsActionBean">Look at screenshots!</stripes:link></p>
</div>
<div id="features">
  <h2>Features</h2>
  <ul>
    <li>Completely free, as in beer, and as in speech</li>
    <li>Create any number of accounts you wish, and add any number of cards to each account</li>
    <li>Traffic secured by SSL</li>
    <li>All sensitive information encrypted in database</li>
    <li>Icons of your web sites automatically found and retrieved</li>
    <li>Translated in various languages (English and French are currently supported)</li>
    <li>Uses Javascript and AJAX for a better user experience, but degrades nicely without Javascript support</li>
  </ul>
</div>
<div id="faq">
  <h2>FAQ</h2>
  <div class="question">
    Is it secure?
  </div>
  <div class="answer">
    Yes. The traffic between your computer and the server where the passwords are stored is protected by SSL.
  </div>
  <div class="question">
    What about the administrator of MemWords? If he looks into the database, he will find out my passwords, right?
  </div>
  <div class="answer">
    No. Most of the information in the database is encrypted : the passwords, but also the logins, notes, URLs, icons, etc.
  </div>
  <div class="question">
    It's encrypted, but the administrator must know the key, else the information could not be decrypted, isn't it?
  </div>
  <div class="answer">
    No. The key used to encrypt <em>your</em> information is protected by <em>your</em> master password, that you're the only one to know.
  </div>
  <div class="question">
    What are the algorithms used to protect my passwords?
  </div>
  <div class="answer">
    There are two main cryptographic algorithms used by MemWords : AES-128 and SHA-256.
  </div>
  <div class="question">
    Why not using AES-256?
  </div>
  <div class="answer">
    First reason: MemWords is a hobby project, hosted freely on <a href="http://code.google.com/appengine/">Google App Engine</a>. And Google App Engine doesn't support AES-256.<br/>
    Second reason: AES-128 is secure enough. Your master password is the weakest link anyway. If you're really paranoïd about your passwords, don't store them anywhere but your brain.
  </div>
  <div class="question">
    What if my master password is compromized?
  </div>
  <div class="answer">
    Too bad. You still have the possibility to change it, or to destroy your account. But the security of your account
    depends on the strength of your master password. Choose a strong one, and keep it to yourself.
  </div>
  <div class="question">
    Do you guarantee that my information won't ever be lost?
  </div>
  <div class="answer">
    No. MemWords is a hobby project. I give you the application, and you may browse the source code if you
    will, but use it at your own risks.
  </div>
</div>