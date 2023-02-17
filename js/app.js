let indexBlockIng = 1;
let currTopIng = 381;
let imageTmpUrl;

function setUserName() {
    let username = document.cookie.match('(^|;) ?' + "username" + '=([^;]*)(;|$)');

    let divUserName = document.createElement('div');

    if (username) {
        divUserName.innerHTML = '<div>\n' +
            ' <a id="signInButton" href="user.html" target="_self">' + username[2] + '</a>\n' +
            ' </div>';
    } else {
        divUserName.innerHTML = '<div>\n' +
            ' <a id="signInButton" href="signIn.html" target="_self"> Войти </a>\n' +
            ' </div>';
    }

    document.getElementById("headerBlock").append(divUserName);
}

function deleteIngBlock(id) {
    document.getElementById("inputIngRec" + id).remove();
    document.getElementById("inputGrRec" + id).remove();
    document.getElementById("deleteIng" + id).remove();

    document.getElementById('addIngButton').style.top = document.getElementById('addIngButton').offsetTop - 50 + 'px';
    document.getElementById('addIngText').style.top = document.getElementById('addIngText').offsetTop - 50 + 'px';
    document.getElementById('lineAddIngRec').style.top = document.getElementById('lineAddIngRec').offsetTop - 50 + 'px';
    document.getElementById('inputStepRec').style.top = document.getElementById('inputStepRec').offsetTop - 50 + 'px';


    for (let i = Number(id) + 1; i <= indexBlockIng; i++) {
        let newIdIng = "inputIngRec" + i;
        let newIdGr = "inputGrRec" + i;
        let newIdImg = "deleteIng" + i;

        if (document.getElementById(newIdIng)) {
            document.getElementById(newIdIng).style.top = document.getElementById(newIdIng).offsetTop - 50 + 'px';
            document.getElementById(newIdGr).style.top = document.getElementById(newIdGr).offsetTop - 50 + 'px';
            document.getElementById(newIdImg).style.top = document.getElementById(newIdImg).offsetTop - 50 + 'px';
        }
    }

    toggleButtonAddRec();
}

function addBlock() {
    document.getElementById('createRecButton').disabled = true;
    for (let i = 1; i <= indexBlockIng; i++) {
        let newIdIng = "inputIngRec" + i;

        if (document.getElementById(newIdIng)) {
            currTopIng = document.getElementById(newIdIng).offsetTop;
        }
    }

    currTopIng += 50;
    indexBlockIng++;

    let newIdIng = "inputIngRec" + indexBlockIng;
    let newIdGr = "inputGrRec" + indexBlockIng;
    let newIdImg = "deleteIng" + indexBlockIng;

    let divId = document.createElement('div');
    divId.innerHTML = '<input class="addIngRec" type="text" ' +
        'placeholder="Добавьте название ингредиента" minlength="2" maxlength="100" required\n' +
        '            oninput="this.value = this.value.replace(/[^а-яёЁ\\s]/gi, \'\');"\n' +
        '            id=' + newIdIng + ' onchange="toggleButtonAddRec()">';

    let divGr = document.createElement('div');
    divGr.innerHTML = '<div>\n' +
        '        <input class="addGrRec" type="text" placeholder="граммы" minlength="1" maxlength="5" required\n' +
        '               oninput="this.value = this.value.replace(/[^0-9\\s]/gi, \'\');"\n' +
        '               id=' + newIdGr + ' onchange="toggleButtonAddRec()">\n' +
        '      </div>';

    let divImg = document.createElement('div');
    divImg.innerHTML = '<div>\n' +
        '        <button type="button" class="deleteIngButton" id=' + newIdImg + '>\n' +
        '          <img src="../img/deleteIng.png" class="deleteIngImg" alt="Удалить ингредиент" ' +
        'onclick="deleteIngBlock(' + indexBlockIng + ')">\n' +
        '        </button>\n' +
        '      </div>';

    document.getElementById("addedIngRec").append(divId);
    document.getElementById("addedIngRec").append(divGr);
    document.getElementById("addedIngRec").append(divImg);

    document.getElementById(newIdIng).style.top = currTopIng + 'px';
    document.getElementById(newIdGr).style.top = currTopIng + 'px';
    document.getElementById(newIdImg).style.top = currTopIng + 11 + 'px';
}

function addIngBlock() {
    addBlock();

    document.getElementById('addIngButton').style.top = document.getElementById('addIngButton').offsetTop + 50 + 'px';
    document.getElementById('addIngText').style.top = document.getElementById('addIngText').offsetTop + 50 + 'px';
    document.getElementById('lineAddIngRec').style.top = document.getElementById('lineAddIngRec').offsetTop + 50 + 'px';
    document.getElementById('inputStepRec').style.top = document.getElementById('inputStepRec').offsetTop + 50 + 'px';
}

function toggleButtonAddRec() {
    let nameRec = document.getElementById('inputNameRec').value;
    let DecRec = document.getElementById('inputDecRec').value;

    let inputIngRecOne = document.getElementById('inputIngRec1').value;

    let inputGrRecOne = document.getElementById('inputGrRec1').value;

    let inputStepRec = document.getElementById('inputStepRec').value;
    let inputTimeCook = document.getElementById('inputTimeCook').value;

    let typeCuisAddRec = document.getElementById('typeCuisAddRec');
    let categoryAddRec = document.getElementById('categoryAddRec');

    document.getElementById('createRecButton').disabled = !((nameRec.length > 1) && (DecRec.length > 1)
        && (inputIngRecOne.length > 1) && (inputGrRecOne.length > 0)
        && (inputStepRec.length > 20) && (inputTimeCook.length > 0)
        && !(typeCuisAddRec.selectedIndex === 0) && !(categoryAddRec.selectedIndex === 0) && (imageTmpUrl !== ""));

    for (let i = 1; i <= indexBlockIng; i++) {
        let newIdIng = "inputIngRec" + i;
        let newIdGr = "inputGrRec" + i;

        if (document.getElementById(newIdIng)) {
            console.log(document.getElementById(newIdIng).value.length);

            if ((document.getElementById(newIdIng).value.length < 2)
                || (document.getElementById(newIdGr).value.length === 0)) {
                document.getElementById('createRecButton').disabled = true;
            }
        }
    }
}

function toggleButtonSignIn() {
    let username = document.getElementById('inputLogin').value;
    let password = document.getElementById('inputPassword').value;

    document.getElementById('enterButton').disabled = !((username.length > 1) && (password.length > 7));
    console.log(document.cookie);
    document.cookie = "username=" + username;
    document.cookie = "logged_in=yes";
}

function searchKeywords() {
    let keyword = document.getElementById('searchLine').value;

    document.cookie = "keyword=" + keyword;
    //console.log(document.cookie);
    console.log(keyword);
}

function searchFilters() {
    let filter1 = document.getElementById('categoryIndex').value;
    let filter2 = document.getElementById('typeCuis').value;

    document.cookie = "filter1=" + filter1;
    document.cookie = "filter2=" + filter2;
    console.log(document.cookie);
}

function remEvent(event) {
    event.preventDefault();
    alert("Пароль или Логин введены неверно");
}

function isCorrectSignIn() {
    let username = document.getElementById("inputLogin").value;
    let password = document.getElementById("inputPassword").value;
    document.removeEventListener('submit', remEvent);

    if ((password.length < 8) || (password.length > 12)
        || (username.length < 2) || (username.length > 30)) {
        document.addEventListener('submit', remEvent);
    } /*Добавить проверку, существует ли такой пользователь и корректность данных*/
}

function changeHeightRec() {
    setUserName();

    let top = 145;
    let offset = 21;

    let nameHeight = document.getElementById('infoNameRec').scrollHeight;
    document.getElementById('infoNameRec').style.height = nameHeight + 'px';

    document.getElementById('infoRecLeftBlock').style.top = offset + top + nameHeight + 'px';

    let decHeight = document.getElementById('infoDecRec').scrollHeight + 5;
    document.getElementById('infoDecRec').style.height = decHeight + 'px';

    let ingHeight = document.getElementById('infoIngRec').scrollHeight + 5;
    document.getElementById('infoIngRec').style.height = ingHeight + 'px';
    document.getElementById('infoIngBlock').style.top = offset + decHeight + 'px';

    document.getElementById('infoStepBlock').style.top = offset + offset + 40 + decHeight + ingHeight + 'px';
    document.getElementById('infoStepRec').style.height = document.getElementById('infoStepRec').scrollHeight + 'px';

    document.getElementById('addFavorites').disabled = !(document.getElementById('signInButton').innerText !== "Войти");

    let rec = [];
    let nameRec = document.cookie.match('(^|;) ?' + "nameRec" + '=([^;]*)(;|$)');
    let username = document.cookie.match('(^|;) ?' + "username" + '=([^;]*)(;|$)');
    let url_ = "http://localhost:8080/povarenok/recipes/" + nameRec[2];
    console.log(document.cookie);

    //var name = document.getElementById(`NameRec${i}`).value;
    $.ajax({
        type: 'GET',
        url: url_, // тут должно быть имя выбираться автоматически
        dataType: 'json', // тип ожидаемых данных,
        contentType: 'application/json',
        success: function (data) {
            for (const key in data) {
                rec.push(data[key]);
            }

            document.getElementById('infoNameRec').innerText =rec[2];

            document.getElementById('infoDecRec').innerText = rec[9];

            document.getElementById('infoStepRec').innerText = rec[10] ;

            document.getElementById('infoCatRec').innerText = rec[6];

            document.getElementById('infoTypeCusRec').innerText =rec[5];

            document.getElementById('infoAuthorRec').innerText = rec[1] ;

            let strIng = "";

            // let ing = []
             for (let i = 0; i<rec[8].length; i++){
                 let dig = i+1;
                 strIng += dig + ". " + rec[8][i]["name"] + " " + rec[8][i]["grams"] + " грамм \n " ;
             }
            document.getElementById('infoIngRec').innerText = strIng;


            document.getElementById('infoTimeCookingRec').innerText = rec[7];

            document.getElementById('infoDateRec').innerText = rec[4];

            let src = "../downloads/" + rec[3];

            console.log(src);
            document.getElementById('infoRecImage').setAttribute('src', src);

            console.log(rec);
        }, // обработка ответа от сервера
        error: function (data) {
            console.log(data);
        },
    });

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/povarenok/users/' + username[2], // адрес запроса
        dataType: 'json', // тип ожидаемых данных,
        contentType: 'application/json',
        success: function (data) {
            let countFavRec = data["savedRecipes"].length; /*ДОБАВИТЬ ПОЛУЧЕНИЕ ЧИСЛА ИЗБРАННЫХ РЕЦЕПТОВ*/

            for (let i = 0; i < countFavRec; i++) {
                console.log(data["savedRecipes"][i]["name"] );
                console.log( nameRec);
               if(data["savedRecipes"][i]["name"] === nameRec[2]){
                   document.getElementById('addFavorites').innerText = "Удалить из избранного";

               }
            }
        }, // обработка ответа от сервера
        error: function (data) {
            console.log(data);
        },
    });
}

function toggleButtonSignUp() {
    let username = document.getElementById('signUpLogin').value;
    let password = document.getElementById('signUpPassword').value;
    let email = document.getElementById('signUpEmail').value;

    document.getElementById('registerButton').disabled = !((username.length > 1) && (password.length > 7)
        && (email.length > 0));

    document.cookie = "username=" + username;
    document.cookie = "logged_in=yes";
}

function isCorrectSignUp() {
    /*Добавить проверку, существует ли такой пользователь */
}

let files;
let index;

let addedCatAddRec = false;
let addedCusAddRec = false;

function dragAndDropFunc() {
    const types = ['image/jpeg', 'image/png'];
    imageTmpUrl = "";
    let dragAndDrop = document.querySelector('.dropImgZone'),
        images = document.querySelector('.images');

    dragAndDrop.addEventListener('dragenter', (e) => {
        e.preventDefault();
        dragAndDrop.classList.add('active');
    })
    dragAndDrop.addEventListener('dragleave', (e) => {
        e.preventDefault();
        dragAndDrop.classList.remove('active');
    })
    dragAndDrop.addEventListener('dragover', (e) => {
        e.preventDefault();
    })
    dragAndDrop.addEventListener('drop', (e) => {
        e.preventDefault();
        files = e.dataTransfer.files;
        index = e.dataTransfer.files.length - 1;
        if (types.includes(files[e.dataTransfer.files.length - 1].type)) {
            if (document.getElementById("addImg")) {
                document.getElementById("addImg").remove();
            }
            if (document.getElementById("addImgRecText")) {
                document.getElementById("addImgRecText").remove();
            }

            imageTmpUrl = URL.createObjectURL(files[e.dataTransfer.files.length - 1]);
            images.innerHTML = `<img src="${imageTmpUrl}" class="image" alt="">`;
            toggleButtonAddRec();
        }

        dragAndDrop.classList.remove('active');
    })

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/povarenok/categories', // адрес запроса
        dataType: 'json', // тип ожидаемых данных,
        contentType: 'application/json',
        success: function (data) {
            if (!addedCatAddRec) {
                for (let i = 0; i < data.length; i++) {
                    let divCat = document.createElement('option');
                    divCat.innerHTML = '<option value="' + data[i]["id"] + '">' + data[i]["name"] + '</option>';
                    document.getElementById("categoryAddRec").append(divCat);
                    if (i === data.length - 1) {
                        addedCatAddRec = true;
                    }
                }
            }
            console.log(data);
        }, // обработка ответа от сервера
        error: function (data) {
            console.log(data);
        },
    });

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/povarenok/cuisines', // адрес запроса
        dataType: 'json', // тип ожидаемых данных,
        contentType: 'application/json',
        success: function (data) {
            if (!addedCusAddRec) {
                for (let i = 0; i < data.length; i++) {
                    let divCat = document.createElement('option');
                    divCat.innerHTML = '<option value="' + data[i]["id"] + '">' + data[i]["name"] + '</option>';
                    document.getElementById("typeCuisAddRec").append(divCat);
                    if (i === data.length - 1) {
                        addedCusAddRec = true;
                    }
                }
            }
            console.log(data);
        }, // обработка ответа от сервера
        error: function (data) {
            console.log(data);
        },
    });

    setUserName();
}

function loadInfoSearchRec() {
    setUserName();
    let keyword = document.cookie.match('(^|;) ?' + "keyword" + '=([^;]*)(;|$)');
    let filter1 = document.cookie.match('(^|;) ?' + "filter1" + '=([^;]*)(;|$)');
    let filter2 = document.cookie.match('(^|;) ?' + "filter2" + '=([^;]*)(;|$)');
    //console.log(keyword);

    if (keyword != null && keyword[2] !== "") {
        let searchRec = keyword[2];

        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/povarenok/recipes/keywords/' + searchRec,
            dataType: 'json', // тип ожидаемых данных,
            contentType: 'application/json',
            success: function (data) {
                let countSearchBlock = data.length; /*ДОБАВИТЬ ПОЛУЧЕНИЕ ЧИСЛА НАЙДЕННЫХ РЕЦЕПТОВ*/

                //document.getElementById('textCountRes').innerText = "ВСЕГО РЕЦЕПТОВ НАЙДЕНО: " + countSearchBlock;
                document.getElementById('textCountRes').innerText = "ВСЕГО РЕЦЕПТОВ НАЙДЕНО: " + countSearchBlock;
                document.getElementById('textFilterRes').innerText = "Ключевые слова: " + searchRec;

                let currLeftOff = 73;
                let currTopOff = 310;

                for (let i = 0; i < data.length; i++) {
                    let newIdRecImg = "recImage" + i;
                    let newIdNameRec = "nameRec" + i;
                    let newIdNameAuthor = "nameAuthor" + i;
                    let newIdTimeCooking = "timeCooking" + i;
                    let newIdBlockSearch = "blockSearch" + i;

                    let divId = document.createElement('div');

                    let nameRec = "\'" + data[i]["name"] + "\'" ;
                    let src = "../downloads/" + data[i]["imageUrl"];

                    divId.innerHTML = '<div class="blockSearchRec" id=' + newIdBlockSearch + '>\n' +
                        ' <div><img src="' + src + '" class="recImage" id=' + newIdRecImg + ' alt="ИзображениеРецепта"></div>\n' +
                        ' <div><a href="recipe.html" onclick=" setNameRec(' + nameRec + ')" class="nameRec" id=' + newIdNameRec + ' target="_self"> ' + data[i]["name"] + ' </a></div>\n' +
                        ' <div class="nameAuthor" id=' + newIdNameAuthor + '> Автор: ' + data[i]["userLogin"] + '</div>\n' +
                        ' <div class="timeCookingRec" id=' + newIdTimeCooking + '> Время приготовления: ' + data[i]["cookingTime"] + ' минут</div>\n' +
                        '</div>';

                    document.getElementById("SearchingRec").append(divId);

                    document.getElementById(newIdBlockSearch).style.left = currLeftOff + 'px';
                    document.getElementById(newIdBlockSearch).style.top = currTopOff + 'px';

                    currLeftOff += 360;

                    if ((i !== 0) && ((i + 1) % 4 === 0)) {
                        currTopOff += 430;
                        currLeftOff = 73;
                    }
                }
                document.cookie = "keyword=";
                //document.cookie = "keyword=; path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT;";
                console.log(document.cookie);
            }, // обработка ответа от сервера
            error: function (data) {
                console.log(1);
            },
        });
    } else if (filter1 != null && filter2 != null && filter1[2] !== "" && filter2[2] !== "") {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/povarenok/recipes/' + filter1[2] + '/' + filter2[2],
            dataType: 'json', // тип ожидаемых данных,
            contentType: 'application/json',
            success: function (data) {

                let countSearchBlock = data.length; /*ДОБАВИТЬ ПОЛУЧЕНИЕ ЧИСЛА НАЙДЕННЫХ РЕЦЕПТОВ*/

                document.getElementById('textCountRes').innerText = "ВСЕГО РЕЦЕПТОВ НАЙДЕНО: " + countSearchBlock;
                document.getElementById('textFilterRes').innerText = "фильтры: " + filter1[2] + ", " + filter2[2];

                let currLeftOff = 73;
                let currTopOff = 310;

                for (let i = 0; i < data.length; i++) {
                    let newIdRecImg = "recImage" + i;
                    let newIdNameRec = "nameRec" + i;
                    let newIdNameAuthor = "nameAuthor" + i;
                    let newIdTimeCooking = "timeCooking" + i;
                    let newIdBlockSearch = "blockSearch" + i;

                    let divId = document.createElement('div');
                    let nameRec = "\'" + data[i]["name"] + "\'" ;

                    divId.innerHTML = '<div class="blockSearchRec" id=' + newIdBlockSearch + '>\n' +
                        ' <div><img src="../img/exampleRec.jpg" class="recImage" id=' + newIdRecImg + ' alt="ИзображениеРецепта"></div>\n' +
                        ' <div><a href="recipe.html" onclick=" setNameRec(' + nameRec + ')" class="nameRec" id=' + newIdNameRec + ' target="_self"> ' + data[i]["name"] + ' </a></div>\n' +
                        ' <div class="nameAuthor" id=' + newIdNameAuthor + '> Автор: ' + data[i]["userLogin"] + '</div>\n' +
                        ' <div class="timeCookingRec" id=' + newIdTimeCooking + '> Время приготовления: ' + data[i]["cookingTime"] + ' минут</div>\n' +
                        '</div>';

                    document.getElementById("SearchingRec").append(divId);

                    document.getElementById(newIdBlockSearch).style.left = currLeftOff + 'px';
                    document.getElementById(newIdBlockSearch).style.top = currTopOff + 'px';

                    currLeftOff += 360;

                    if ((i !== 0) && ((i + 1) % 4 === 0)) {
                        currTopOff += 430;
                        currLeftOff = 73;
                    }
                }
                document.cookie = "filter1=";
                document.cookie = "filter2=";
                console.log(document.cookie);
            }, // обработка ответа от сервера
            error: function (data) {
                console.log(data);
            },
        });
    }
}

let addedCat = false;
let addedCus = false;

function getRecentRec(){
    let countSearchBlock = 4;
    let currLeftOff = 73;

    $.ajax({
        type: 'GET',
        url: `http://localhost:8080/povarenok/recipes/last?count=${countSearchBlock}`, // адрес запроса
        dataType: 'json', // тип ожидаемых данных,
        contentType: 'application/json',
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                let newIdRecImg = "recImage" + i;
                let newIdNameRec = "nameRec" + i;
                let newIdNameAuthor = "nameAuthor" + i;
                let newIdTimeCooking = "timeCooking" + i;
                let newIdBlockSearch = "block" + i;

                let divId = document.createElement('div');

                let src = "../downloads/" + data[i]["imageUrl"];
                let nameRec = "\'" + data[i]["name"] + "\'" ;

                console.log(nameRec);

                divId.innerHTML = '<div class="blockRec" id=' + newIdBlockSearch + '>\n' +
                    '  <div><img src="' + src + '" class="recImage" id=' + newIdRecImg + ' alt="ИзображениеРецепта"></div>\n' +
                    '  <div><a href="recipe.html" onclick=" setNameRec(' + nameRec + ')" class="nameRec" id=' + newIdNameRec + ' target="_self">' + data[i]["name"] + '</a></div>\n' +
                    '  <div class="nameAuthor" id=' + newIdNameAuthor + '> Автор: ' + data[i]["userLogin"] + '</div>\n' +
                    '  <div class="timeCookingRec" id=' + newIdTimeCooking + '> Время приготовления: ' + data[i]["cookingTime"] + ' минут</div>\n' +
                    '</div>';

                document.getElementById("indexRec").append(divId);

                document.getElementById(newIdBlockSearch).style.left = currLeftOff + 'px';

                currLeftOff += 360;
            }
        }, // обработка ответа от сервера
        error: function (data) {
            console.log(data);
        },
    });
}

function addCatIndex(data){
    if (!addedCat) {
        for (let i = 0; i < data.length; i++) {
            let divCat = document.createElement('option');
            divCat.innerHTML = '<option value="' + data[i]["id"] + '">' + data[i]["name"] + '</option>';
            document.getElementById("categoryIndex").append(divCat);
            if (i === data.length - 1) {
                addedCat = true;
            }
        }
    }
    console.log(data);
}

function getCateg(callback){
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/povarenok/categories', // адрес запроса
        dataType: 'json', // тип ожидаемых данных,
        contentType: 'application/json',
        success: function (data) {
            callback(data);
        }, // обработка ответа от сервера
        error: function (data) {
            console.log(data);
        },
    });
}

//module.exports = {getCateg};
//let exports = module.exports = {getCateg};

function getCuisines(){
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/povarenok/cuisines', // адрес запроса
        dataType: 'json', // тип ожидаемых данных,
        contentType: 'application/json',
        success: function (data) {
            if (!addedCus) {
                for (let i = 0; i < data.length; i++) {
                    let divCat = document.createElement('option');
                    divCat.innerHTML = '<option value="' + data[i]["id"] + '">' + data[i]["name"] + '</option>';
                    document.getElementById("typeCuis").append(divCat);
                    if (i === data.length - 1) {
                        addedCus = true;
                    }
                }
            }
            console.log(data);
        }, // обработка ответа от сервера
        error: function (data) {
            console.log(data);
        },
    });
}

function loadInfoIndexRec() {
    getRecentRec();
    getCateg(addCatIndex);
    getCuisines();
    setUserName();
}

function setNameRec(nameRec){
    document.cookie = "nameRec=" + nameRec;
}

let flag = false;

function loadInfoUserRec() {
    let username = document.cookie.match('(^|;) ?' + "username" + '=([^;]*)(;|$)');

    let divUserName = document.createElement('div');

    if (username) {
        divUserName.innerHTML = '<div id="textUserName">' + username[2] + '</div>';
    }

    document.getElementById("headerBlock").append(divUserName);

    if(!flag) {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/povarenok/users/' + username[2], // адрес запроса
            dataType: 'json', // тип ожидаемых данных,
            contentType: 'application/json',
            success: function (data) {

                let countAddedRec = data["addedRecipes"].length; /*ДОБАВИТЬ ПОЛУЧЕНИЕ ЧИСЛА СОЗДАННЫХ ПОЛЬЗОВАТЕЛЕМ РЕЦЕПТОВ*/
                let countFavRec = data["savedRecipes"].length; /*ДОБАВИТЬ ПОЛУЧЕНИЕ ЧИСЛА ИЗБРАННЫХ РЕЦЕПТОВ*/

                let currTopOff = 70;

                let heightBlockUserRecOne = 70 + 230 * countAddedRec;
                document.getElementById('blockUserRecOne').style.height = heightBlockUserRecOne + 'px';
                for (let i = 0; i < countAddedRec; i++) {
                    let newIdRecImg = "recImage" + i;
                    let newIdNameRec = "nameRec" + i;
                    let newIdNameAuthor = "nameAuthor" + i;
                    let newIdTimeCooking = "timeCooking" + i;
                    let newIdBlockUserRec = "subblockUserRecOne" + i;

                    let divId = document.createElement('div');

                    let src = "../downloads/" + data["addedRecipes"][i]["imageUrl"];
                    let nameRec = "\'" + data["addedRecipes"][i]["name"] + "\'";

                    divId.innerHTML = '<div class="subblockUserRec" id=' + newIdBlockUserRec + '>\n' +
                        ' <div><img src="' + src + '" class="recImage" id=' + newIdRecImg + ' alt="ИзображениеРецепта"></div>\n' +
                        ' <div><a href="recipe.html" onclick=" setNameRec(' + nameRec + ')" class="nameUserRec" id=' + newIdNameRec + ' target="_self">' + data["addedRecipes"][i]["name"] + '</a></div>\n' +
                        ' <div class="nameAuthorRec" id=' + newIdNameAuthor + '> Автор: ' + data["addedRecipes"][i]["userLogin"] + '</div>\n' +
                        ' <div class="UserTimeCooking" id=' + newIdTimeCooking + '> Время приготовления: ' + data["addedRecipes"][i]["cookingTime"] + ' минут</div>\n' +
                        '</div>';

                    document.getElementById("blockUserRecOne").append(divId);

                    document.getElementById(newIdBlockUserRec).style.top = currTopOff + 'px';

                    document.getElementById(newIdRecImg).style.top = 5 + 'pt';
                    document.getElementById(newIdRecImg).style.left = 10 + 'pt';

                    currTopOff += 230;
                }

                currTopOff = 70;
                let heightBlockUserRecTwo = 70 + 230 * countFavRec;
                document.getElementById('blockUserRecTwo').style.height = heightBlockUserRecTwo + 'px';

                for (let i = 0; i < countFavRec; i++) {
                    console.log(countFavRec + 12345);
                    let newIdRecImg = "recImage" + i + countAddedRec;
                    let newIdNameRec = "nameRec" + i + countAddedRec;
                    let newIdNameAuthor = "nameAuthor" + i + countAddedRec;
                    let newIdTimeCooking = "timeCooking" + i + countAddedRec;
                    let newIdBlockUserRec = "subblockUserRecOne" + i + countAddedRec;

                    let divId = document.createElement('div');

                    let src = "../downloads/" + data["savedRecipes"][i]["imageUrl"];

                    divId.innerHTML = '<div class="subblockUserRec" id=' + newIdBlockUserRec + '>\n' +
                        ' <div><img src="' + src + '" class="recImage" id=' + newIdRecImg + ' alt="ИзображениеРецепта"></div>\n' +
                        ' <div><a href="recipe.html" class="nameUserRec" id=' + newIdNameRec + ' target="_self">' + data["savedRecipes"][i]["name"] + '</a></div>\n' +
                        ' <div class="nameAuthorRec" id=' + newIdNameAuthor + '> Автор: ' + data["savedRecipes"][i]["userLogin"] + '</div>\n' +
                        ' <div class="UserTimeCooking" id=' + newIdTimeCooking + '> Время приготовления: ' + data["savedRecipes"][i]["cookingTime"] + ' минут</div>\n' +
                        '</div>';

                    document.getElementById("blockUserRecTwo").append(divId);

                    document.getElementById(newIdBlockUserRec).style.top = currTopOff + 'px';

                    document.getElementById(newIdRecImg).style.top =
                        5 + 'pt';
                    document.getElementById(newIdRecImg).style.left = 10 + 'pt';

                    currTopOff += 230;
                }
                flag = true;
            }, // обработка ответа от сервера
            error: function (data) {
                console.log(data);
            },
        });
    }
}

//запрос сохраняет новый рецепт в базу данных исполь в файлк addRecipe.html
function postAddReq() {
    let dateTime = new Date();
    let dateNew = dateTime.toISOString().split('T')[0];
    console.log(dateNew);

    let url = Date.parse(new Date() + '') + '.png';

    const link = document.createElement('a'); // создаём ссылку
    link.href = document.querySelector('.images').querySelector('img').getAttribute('src');
    link.setAttribute('download', url); // чтобы при скачивании файл назывался как нам нужно - указываем явно имя в атрибут ссылки
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);

    let username = document.cookie.match('(^|;) ?' + "username" + '=([^;]*)(;|$)');

    let userLogin = username[2];
    let name = document.getElementById('inputNameRec').value;
    let imageUrl = url;
    let dateAdded = dateNew;
    let cuisine = document.getElementById('typeCuisAddRec').value;
    let category = document.getElementById('categoryAddRec').value;
    let cookingTime = document.getElementById('inputTimeCook').value;
    let description = document.getElementById('inputDecRec').value;
    let recipe = document.getElementById('inputStepRec').value;

    let ingredients = [];
    let div = document.querySelectorAll('.addIngRec');
    console.log(div);

    for (let i = 1; i < div.length + 1; i++) {

        let newIdIng = `inputIngRec${i}`;
        let newIdGr = `inputGrRec${i}`;

        let ingredient = {
            idRecipe: null,
            name: document.getElementById(newIdIng).value,
            grams: document.getElementById(newIdGr).value
        };

        ingredients.push(ingredient);
    }

    console.log(ingredients);

    let rec = {
        userLogin: userLogin,
        name: name,
        imageUrl: imageUrl,
        dateAdded: dateAdded,
        cuisine: cuisine,
        category: category,
        cookingTime: cookingTime,
        ingredients: ingredients,
        description: description,
        recipe: recipe
    }

    console.log(JSON.stringify(rec));

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/povarenok/recipes/new', // адрес запроса
        data: JSON.stringify(rec), // данные запроса
        dataType: 'json', // тип ожидаемых данных,
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
        }, // обработка ответа от сервера
        error: function (data) {
            console.log(data);
        },
    });
}

//запрос сохраняет пользователя в базу данных
function postSignUpReq() {
    let login = document.getElementById('signUpLogin').value;
    let pass = document.getElementById('signUpPassword').value;
    let email = document.getElementById('signUpEmail').value;

    let user = {
        login: login,
        password: pass,
        email: email
    }

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/povarenok/registration', // адрес запроса
        data: JSON.stringify(user), // данные запроса
        dataType: 'json', // тип ожидаемых данных,
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
        }, // обработка ответа от сервера
        error: function (data) {
            console.log(data);
        },
    });
}

//запрос сохраняет рецепт с указанным наименованием в избранные рецепты пользователя с указанным логином
function postSaveReq() {
    if (document.getElementById('addFavorites').innerText === "Добавить в избранное") {
        document.getElementById('addFavorites').innerText = "Удалить из избранного";
        let username = document.cookie.match('(^|;) ?' + "username" + '=([^;]*)(;|$)');
        let login = username[2];
        let name =  document.getElementById('infoNameRec').value; //------раскоменитить когда будут готова реализация обработки пробелов
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/povarenok/recipes/' + login + '/save/' + name, // адрес запроса
            dataType: 'json', // тип ожидаемых данных,
            contentType: 'application/json',
            success: function (data) {
                console.log(data);

            }, // обработка ответа от сервера
            error: function (data) {
                console.log(data);
            },
        });

    } else if (document.getElementById('addFavorites').innerText === "Удалить из избранного") {
        document.getElementById('addFavorites').innerText = "Добавить в избранное";
        let username = document.cookie.match('(^|;) ?' + "username" + '=([^;]*)(;|$)');
        let login = username[2];
        let name =  document.getElementById('infoNameRec').value; //------раскоменитить когда будут готова реализация обработки пробелов
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/povarenok/recipes/' + login + '/delete/' + name, // адрес запроса
            dataType: 'json', // тип ожидаемых данных,
            contentType: 'application/json',
            success: function (data) {
                console.log(data);

            }, // обработка ответа от сервера
            error: function (data) {
                console.log(data);
            },
        });

    }

}

// запрос возвращает всю информацию о рецепте в формате JSON по наименованию
function getInfoRec() {
    let rec = []
    let name = document.getElementById(`NameRec${i}`).value;
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/povarenok/recipes/' + name, // тут должно быть имя выбираться автоматически
        dataType: 'json', // тип ожидаемых данных,
        contentType: 'application/json',
        success: function (data) {
            for (const key in data) {
                rec.push(data[key])
            }
            console.log(rec);
        }, // обработка ответа от сервера
        error: function (data) {
            console.log(data);
        },
    });
}
