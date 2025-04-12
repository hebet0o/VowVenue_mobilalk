**Javítási segítség:**

**Fordítás-Futtatási hiba**, check for yourself (Elvileg nem kellene lennie, szépen fordul)

**Firebase Authentikáció:** MainActivity.java 52. sor login method, illetve 75. sor guestLogin, RegisterActivity.java 64. sor firebaseReg method

**ConstraintLayout** és még egy másik layout típus használata: Activity_main.xml 2. sor (ConstraintLayout), Activity_register (LinearLayout)

**Reszponzivitás:** Should be responsive on all screens, a képek néhol szétcsúsznak, de landscape és portrait view-k meg vannak csinálva

**Animacio:** res/anim mappában van egy fade_in.xml, használata a MainActivity.java 52. sorában található "Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);"

**Intentek használata:** navigáció meg van valósítva az activityk között (minden activity elérhető): Login, Register közötti lépkedés megoldva, Intentekkel MainActivity: 65. sor, 83. sor, 93. sor, RegisterActivity: 54. sor, 59. sor, 83. sor

**Szubjektív:** 🙏

ty.
