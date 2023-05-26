
const functions = require("firebase-functions");
// const fetch = require("node-fetch");

const admin = require("firebase-admin");
admin.initializeApp();

// const database=admin.firestore();
// const url = "https://commodities-api.com/api/latest?access_key=r9tq28c6qws44lfkpsq498rg2xui755thduah66uw3bg5irr35qss6miqfmm&base=PLN&symbols=ALU,XCU,XAU,LCO,NI,RISE,XAG,XPD";
// const settings = {method: "Get"};
exports.scheduledFunction = functions.pubsub.schedule("every 2 minutes")
    .onRun((context) => {
      // refreshActions();
      return console.log("Updated succefully");
    });
/**
 * Wypisuje dane i elo
* */
/*
async function refreshActions() {
  // refreshPrice();

  const citiesRef = database.collection("akcje");
  // const prizesRef = database.collection("prizes");
  const snapshot = await citiesRef.get();
  snapshot.forEach((doc) => {
    const akcja=doc.data();
    // console.log("Akcja:", akcja);
    fetch(url, settings)
        .then((res) => res.json())
        .then((json) => {
          let x=0;
          if (akcja.symbol=="ALU") {
            x=json.data.rates.ALU;
            x=1/x;
            x=Number(x.toFixed(2));
            console.log("Cena ALU", x);
            database.doc("prizes/ALU").update({"price": x});
          } else if (akcja.symbol=="XCU") {
            x=json.data.rates.XCU;
            x=1/x;
            x=Number(x.toFixed(2));
            console.log("Cena XCU", x);
            database.doc("prizes/XCU").update({"price": x});
          } else if (akcja.symbol=="XPD") {
            x=json.data.rates.XPD;
            x=1/x;
            x=Number(x.toFixed(2));
            console.log("Cena XPD", x);
            database.doc("prizes/XPD").update({"price": x});
          } else if (akcja.symbol=="NI") {
            x=json.data.rates.NI;
            x=1/x;
            x=Number(x.toFixed(2));
            console.log("Cena NI", x);
            database.doc("prizes/NI").update({"price": x});
          } else if (akcja.symbol=="LCO") {
            x=json.data.rates.LCO;
            x=1/x;
            x=Number(x.toFixed(2));
            console.log("Cena LCO", x);
            database.doc("prizes/LCO").update({"price": x});
          } else if (akcja.symbol=="XAG") {
            x=json.data.rates.XAG;
            x=1/x;
            x=Number(x.toFixed(2));
            console.log("Cena XAG", x);
            database.doc("prizes/XAG").update({"price": x});
          } else {
            x=json.data.rates.XAU;
            x=1/x;
            x=Number(x.toFixed(2));
            console.log("Cena XAU", x);
            database.doc("prizes/XAU").update({"price": x});
          }
          const ilosc=akcja.ilosc;
          const newValue=ilosc*x;
          let zysk=Number(akcja.wklat-newValue);
          zysk=Number(zysk.toFixed(2));
          const fullId="akcje/"+doc.id;
          database.doc(fullId).update({"zysk": zysk});
        });
    // const fullId="akcje/"+doc.id;
    // database.doc(fullId).update({"zysk": zysk});
    // console.log(fullId, " value:", dataPrice);
  });
}
/**
 * Nowe kursy
 */
/*
async function refreshPrice() {
  fetch(url, settings)
      .then((res) => res.json())
      .then((json) => {
        // 3 wartosci po kolei
        let x=json.data.rates.ALU;
        x=1/x;
        x=Number(x.toFixed(2));
        database.doc("prizes/ALU").update({"price": x});
        console.log("Updated succefully"+ ":", x);
        x=json.data.rates.XAU;
        x=1/x;
        x=Number(x.toFixed(2));
        database.doc("prizes/XAU").update({"price": x});
        console.log("Updated succefully"+ ":", x);
        x=json.data.rates.XCU;
        x=1/x;
        x=Number(x.toFixed(2));
        database.doc("prizes/XCU").update({"price": x});
        console.log("Updated succefully"+ ":", x);
      });
}
*/
