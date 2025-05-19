**Javítási segítség:**

**Fordítás-Futtatási hiba**, check for yourself (Elvileg nem kellene lennie, szépen fordul)

**Firebase Authentikáció:** MainActivity.java 52. sor login method, illetve 75. sor guestLogin, RegisterActivity.java 64. sor firebaseReg method

**ConstraintLayout** és még egy másik layout típus használata: Activity_main.xml 2. sor (ConstraintLayout), Activity_register (LinearLayout)

**Reszponzivitás:** Should be responsive on all screens, a képek néhol szétcsúsznak, de landscape és portrait view-k meg vannak csinálva

**Animacio:** res/anim mappában van egy fade_in.xml, használata a MainActivity.java 52. sorában található "Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);"

**Intentek használata:** navigáció meg van valósítva az activityk között (minden activity elérhető): Login, Register közötti lépkedés megoldva, Intentekkel MainActivity: 65. sor, 83. sor, 93. sor, RegisterActivity: 54. sor, 59. sor, 83. sor

**Szubjektív:** 🙏

ty.

Masodik merfoldko javitasi segitseg:

**Forditasi-Futtatasi hiba:** Same as before, nem kéne lennie, emulatoron, es telefonon is tesztelve
**FireBase Auth:** Same as before, bejelentkezés, regisztráció, logout gomb is bekerült.
**Adatmodell definiálása:** Venue.java file, egy alap helyszínt reprezentál
**Legalább 4 különböző activity használata:**: Done, AddVenueActivity, RegisterActivity, MainActivity, VenueActivity
**Beviteli mezők típusa megfelelő:** Elméletileg mindenhol definiálva van, passwordok, emailek kezelve, többi sima text.
**Layoutok**: ConstraintLayout és még egy másik layout típus használata: Activity_main.xml 2. sor (ConstraintLayout), Activity_register (LinearLayout)
**Reszponziv:** Check for yourself, amihez szükséges volt, van land view, amihez nem, ahoz nem csináltam külön
**Animációk:** res/anim mappában van egy fade_in.xml, illetve slide_in.xml használata a MainActivity.java 52. sorában található "Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);", illetve MainActivity 49, 50. sorban. 
**Intentek használata:** Mozgás az activtyk között lehetséges, bárhonnan lehet menni bárhova (I believe)
**LIfecycle Hook:** VenueActivity.java: onPause(), onResume(), 158., ill. 164. sor.
**Permission:** AndroidManifest.xml:  <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION">, használata AlarmReciever, ill. NotificationHelper segítségével.**
**Rendszerszolg:**: AlarmReciever, NotificationHelper
**CRUD:** Csak Read, ill. Create van, venuekat listázzuk, illetve lehet hozzáadni is. (AddVenueActivity.java)
**Firebase lekérdezések:** VenueActivity.java 171-231. sor, 3 lekérdezés
**Szubjektiv:** pls

ty.
