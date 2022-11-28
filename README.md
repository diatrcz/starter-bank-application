# Házi feladat specifikáció

## Bemutatás

Ahogy a neve is mutatja, ez egy kiadás és bevétel nyomkövető alkalmazás, ahol a felhasználó nyomon tudja követni a bevételeit, illetve a kiadásait. Kiadásoknál kategóriákba szervezheti, hogy éppen mire költött, illetve kördiagram segítségével látható, hogy az adott időintervallumban az egyes kategóriákra mennyi kiadás történt. Hogyha az alkalmazásban szereplő összeg egy bizonyos szint alá csökken -ezt a szintet a felhasználó állítja be- az alkalmazás figyelmezteti a felhasználót erről a tényről. Az applikációban időrendi sorrendben listában egymás alatt jelennek meg a kiadások és a bevételek, illetve felül látszik a felhasználónak még mennyi jövedelme maradt az alkalmazás szerint. Ha a felhasználó szeretné törölhető egyszerre az összes bevétel és kiadás, erre van egy külön clear gomb. 
Az alkalmazás ötletét az alap banki alkalmazások adták. Mondhatni ez egy lebutított banki alkalmazás, csak itt havonta nem kezd új táblát a felhasználó számlamozgásáról. Így minden olyan jövedelemmel rendelkező személynek ideális lehet, aki nem havi viszonylatban szeretné figyelni a kiadásait-bevételeit, hanem mondjuk fél évre. 

## Főbb funkciók

Az alkalmazás felső részén középen látszik a felhasználó jelenlegi bevétele. Alatta a bevételi és költési bejegyzések láthatóak listában, időrendi sorrendben rendezve. A képernyő alján egy Toolbar kap helyet, amelyen van egy „Clear” gomb, amellyel a jövedelem, illetve a bejegyzések törölhetőek, ekkor a bevétel mutató értéke nullára vált. Ezen a Toolbaron helyet kap még egy menü, amelyben hozzáadhatunk bevételt, illetve kiadást, valamint beállíthatjuk azt az összeget, aminek átlépésekor az alkalmazás figyelmeztetést dob (ezt SnackBar formájában teszi meg). Mindhárom opció kiválasztásakor megjelenik egy felugró ablak, amibe beírhatjuk az összeget, illetve kiadás beírásakor kiválaszthatjuk a kategóriát, hogy mire költöttünk. 
Az alkalmazás tetején található még egy Tab jellegű fejléc, amelynek egyik pontjában ez a lista nézet kap helyet, a másikban pedig a kiadásokat ábrázoló kördiagram.

## Választott technológiák:

- UI
- fragmentek
- RecyclerView
- Perzisztens adattárolás
