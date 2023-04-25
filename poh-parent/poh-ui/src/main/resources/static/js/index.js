(function (doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            if (clientWidth >= 1920) {
                docEl.style.fontSize = '100px'; //1rem  = 100px
            } else {
                docEl.style.fontSize = 100 * (clientWidth / 1920) + 'px';
            }
        };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);

let currentUser = getCurrentUser();
console.log(currentUser)
$('#nickname').text(currentUser.nickname)
$('#identity').text(currentUser.identity)
let html = '<a id="logout" style="color: #0e94ea">退出账号</a>'
if (currentUser.toBackstage) {
    html += '&nbsp;|&nbsp;<a id="backstage" style="color: #0e94ea">前往后台</a>'
    $('#right-choose').html(html)
} else {
    $('#right-choose').html(html)
}

$('#backstage').on('click', () => {
    location.href = '../backstage/device/device_list.html'
})

// 退出登录事件
$('#logout').click(() => {
    // 发起请求
    axios({
        method: "get",
        url: '/logout',
        headers: {'Authorization': getToken()}
    }).then((resp) => {
        console.log("登出成功")
    })
    storageUtils.LOCAL.remove(storageConstant.CURRENT_USER_TAG)
    location.href = "../login.html"
})

// 地图
// let map = new AMap.Map('map', {
//     zoom:11,                    //级别
//     enableHighAccuracy: true,   // 是否启用高精度定位
//     center: [119.158744, 26.074581],     //中心点坐标
//     viewMode:'3D'               //使用3D视图
// });
// let marker = new AMap.Marker({
//     position:[119.158744, 26.074581]//位置
// })
// map.add(marker);//添加到地图

// 信号强度的图表
// let chartDom = document.getElementById('signal');
// let myChart = echarts.init(chartDom);
// let option;
//
// option = {
//     xAxis: {
//         type: 'category',
//         data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
//         axisLabel: {
//             color: "#ffffff" //刻度线标签颜色
//         }
//     },
//     yAxis: {
//         type: 'value',
//         axisLabel: {
//             color: "#ffffff" //刻度线标签颜色
//         }
//     },
//     series: [
//         {
//             data: [150, 230, 224, 218, 135, 147, 100],
//             type: 'line',
//             color: '#486fec'
//         }
//     ]
// };
//
// option && myChart.setOption(option);

var chartDom = document.getElementById('signal');
var myChart = echarts.init(chartDom);
var option;

let base = +new Date(2020, 9, 3);
let oneDay = 24 * 3600 * 1000;
let date = [];
let data = [Math.random() * 300];
for (let i = 1; i < 600; i++) {
    var now = new Date((base += oneDay));
    date.push([now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'));
    data.push(Math.round((Math.random() - 0.5) * 20 + data[i - 1]));
}
option = {
    tooltip: {
        trigger: 'axis',
        position: function (pt) {
            return [pt[0], '10%'];
        }
    },
    toolbox: {
        feature: {
            dataZoom: {
                yAxisIndex: 'none'
            },
            restore: {},
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: date,
        axisLabel: {
            color: "#ffffff" //刻度线标签颜色
        }
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        axisLabel: {
            color: "#ffffff" //刻度线标签颜色
        }
    },
    dataZoom: [
        {
            type: 'inside',
            start: 0,
            end: 10
        },
        {
            start: 0,
            end: 10
        }
    ],
    series: [
        {
            name: 'Fake Data',
            type: 'line',
            symbol: 'none',
            sampling: 'lttb',
            itemStyle: {
                color: 'rgb(255, 70, 131)'
            },
            areaStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                        offset: 0,
                        color: 'rgb(255, 158, 68)'
                    },
                    {
                        offset: 1,
                        color: 'rgb(255, 70, 131)'
                    }
                ])
            },
            data: data
        }
    ]
};

option && myChart.setOption(option);

function fnW(str) {
    var num;
    str >= 10 ? num = str : num = "0" + str;
    return num;
}

// 更新时间函数
let setTime = () => {
    var date = new Date();
    var year = date.getFullYear(); //当前年份
    var month = date.getMonth(); //当前月份
    var data = date.getDate(); //天
    var hours = date.getHours(); //小时
    var minute = date.getMinutes(); //分
    var second = date.getSeconds(); //秒
    var day = date.getDay(); //获取当前星期几
    var ampm = hours < 12 ? 'am' : 'pm';
    $('#time').html(fnW(hours) + ":" + fnW(minute) + ":" + fnW(second));
    $('#date').html('<span>' + year + '/' + (month + 1) + '/' + data + '</span><span>' + ampm + '</span><span>周' + day + '</span>')
}
setTime()

// 定时更新时间
let timer = setInterval(setTime, 1000)

// 获取前台展示的设备
const getShowedEquipments = () => {
    axios({
        method: "get",
        url: '/equipment/showed/list/4',
        headers: {'Authorization': getToken()}
    }).then((resp) => {
        if (!resp.data.success || resp.data.data === null) {
            return
        }
        let equipments = resp.data.data;
        let showedHtml = ''
        let connectHtml = ''
        equipments.forEach(equipment => {
            showedHtml += `<tr id="showEquip${equipment.eid}">`
            showedHtml += `<td>${equipment.equipName}</td>`
            showedHtml += `<td>${equipment.equipCode}</td>`
            showedHtml += '<td>' +
                '<img src="images/electric.png" alt="电池" style="width: 15px">100%&nbsp;' +
                '<img src="images/temperature.png" alt="温度" style="width: 15px">32℃' +
                '</td>'
            showedHtml += '</tr>'
            connectHtml += `<tr id="connectEquip${equipment.eid}">`
            connectHtml += `<td>${equipment.equipName}</td>`
            connectHtml += `<td>未连接</td>`
            connectHtml += `<td>否</td>`
            connectHtml += '</tr>'
            markerMap[equipment.equipCode] = new AMap.Marker({
                position: [119.158744, 26.074581]//位置
            })
        })
        for (let markerCode in markerMap) {
            map.add(markerMap[markerCode])
        }
        $('#showedEquipments').html(showedHtml)
        $('#connectState').html(connectHtml)
    })
}
getShowedEquipments()



