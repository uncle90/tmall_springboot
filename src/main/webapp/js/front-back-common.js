/**
 * 空值检查
 * @param name
 * @param value
 * @returns {boolean}
 */
function checkEmpty(name,value){
    if(value==null || value.length==0){
        alert(name+ "不能为空");
        return false;
    }
    return true;
}

/**
 * 数字检查
 * @param name
 * @param value
 * @returns {boolean}
 */
function checkNumber(name,value){
    if(value==null || value.length==0){
        alert(name+ "不能为空");
        return false;
    }
    if(isNaN(value)){
        alert(name+ "必须是数字");
        return false;
    }
    return true;
}

/**
 * 整数检查
 * @param name
 * @param value
 * @returns {boolean}
 */
function checkInt(name,value){
    if(value==null || value.length==0){
        alert(name+ "不能为空");
        return false;
    }
    if(parseInt(value)!=value){
        alert(name+ "必须是整数");
        return false;
    }
    return true;
}

/**
 * 删除操作确认
 * @returns {boolean}
 */
function checkDeleteLink() {
    var confirmDelete = confirm("确认要删除");
    if(confirmDelete)
        return true;
    return false;
}

/**
 * 检查 axios 请求错误类型
 * @param error
 */
function check_error_type(error) {
    if(error && error.response){
        switch(error.response.status){
            case 400:
                error.message = error.response.data.message;//错误的请求
                break;
            case 401:
                error.message = '未授权，请重新登录';
                break;
            case 403:
                error.message = '拒绝访问';
                break;
            case 404:
                error.message = '请求错误，未找到该资源';
                break;
            case 408:
                error.message = '请求超时';
                break;
            case 415:
                error.message = '不支持的媒体类型';
                break;
            case 500:
                error.message = '内部服务错误';
                break;
        }
        alert(error.message);
    }
}

/**
 * 分页组件，跳转到指定页
 * @param page
 * @param vue
 */
function gotoPage(page, vue){
    if(!isNaN(page)){
        vue.list(page);
    }else if(page == 'first' && !vue.pagination.first){
        vue.list(0);
    }else if(page == 'previous' && vue.pagination.hasPrevious){
        vue.list(vue.pagination.number-1);
    }else if(page == 'next' && vue.pagination.hasNext){
        vue.list(vue.pagination.number+1);
    }else if(page == 'last' && !vue.pagination.last){
        vue.list(vue.pagination.totalPages-1);
    }
}

/**
 * 获取地址栏参数的函数
 * @param para
 * @returns {*}
 */
function getUrlParms(para){
    var search=location.search; //页面URL的查询部分字符串
    var arrPara=new Array(); //参数数组。数组单项为包含参数名和参数值的字符串，如“para=value”
    var arrVal=new Array(); //参数值数组。用于存储查找到的参数值
    if(search!=""){
        var index=0;
        search=search.substr(1); //去除开头的“?”
        arrPara=search.split("&");
        for(i in arrPara){
            var paraPre=para+"="; //参数前缀。即参数名+“=”，如“para=”
            if(arrPara[i].indexOf(paraPre)==0&& paraPre.length<arrPara[i].length){
                arrVal[index]=decodeURI(arrPara[i].substr(paraPre.length)); //顺带URI解码避免出现乱码
                index++;
            }
        }
    }
    if(arrVal.length==1){
        return arrVal[0];
    }else if(arrVal.length==0){
        return null;
    }else{
        return arrVal;
    }
}

/**
 * 数字金额格式化（小数位四舍五入）
 * @param num
 * @returns {string}
 */
function formatMoney(num){
    num = new String(num);
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        num = num.substring(0,num.length-(4*i+3))+','+
            num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}

/**
 * 是否为非空数字
 * @param val
 */
function isNumber(val){
    if(val == "" || val == null || val == undefined){
        return false;
    }
    if(!isNaN(val)){
        return true;
    }else{
        return false;
    }
}