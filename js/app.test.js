import jQuery from "./jquery-3.6.2.js";
let sinon = require('sinon');
//const { getCateg } = require('./app.js');
import {getCateg} from "./app.js";
const referee = require("@sinonjs/referee");
const assert = referee.assert;

let page;
let browser;

describe("Index", () => {
        beforeAll(async () => {
              // browser = await puppeteer.launch();
              //  page = await browser.newPage();
              //  await page.goto('http://localhost:63342/Frontend/html/index.html?_ijt=idqn27a265qn6dorprnuq3k98h&_ij_reload=RELOAD_ON_SAVE');
        });

        afterAll(() => {
                sinon.restore();
               // browser.close();
        });

        test("should make an Ajax request categories to the correct URL", async () => {
                sinon.replace(jQuery, "ajax", sinon.fake());
                getCateg(sinon.fake());
                assert(jQuery.ajax.calledWithMatch({ url: 'http://localhost:8080/povarenok/categories' }));
                //expect(jQuery.ajax.calls.mostRecent().args[0]["url"]).toBe('http://localhost:8080/povarenok/categories');
        });

       /* test("assert that <title> is correct", async () => {
                const title = await page.title();
                expect(title).toBe("Povarenok");
        });*/


});

describe("SignIn", () => {
        beforeAll(async () => {
                browser = await puppeteer.launch();
                page = await browser.newPage();
                await page.goto('http://localhost:63342/Frontend/html/signIn.html?_ijt=5o024mqm82c7heta8kov0f1jfr&_ij_reload=RELOAD_ON_SAVE');
        });

        afterAll(() => {
                page.close();
                browser.close();
        });

        test("The \"Войти\" button should be inactive if both fields are empty", async () => {
                await page.click("input[name=inputLogin]");
                await page.type("input[name=inputLogin]", "");

                await page.click("input[name=inputPassword]");
                await page.type("input[name=inputPassword]", "");

                const disabledButton = await page.$eval("button[name=enterButton]", el => el.getAttribute("disable"));
                expect(disabledButton).toBeTruthy();
        });

        test("assert that <title> is correct", async () => {
                const title = await page.title();
                expect(title).toBe("Povarenok");
        });



});