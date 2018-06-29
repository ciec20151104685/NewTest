// PlantWars.cpp : 定义应用程序的入口点。
//

#include "stdafx.h"
#include "PlantWars.h"

#define MAX_LOADSTRING 100
#define WNDWIDTH       380         /* Game window width                */
#define WNDHEIGHT      550         /* Game window height               */

// 全局变量: 
HINSTANCE hInst;                                // 当前实例
WCHAR szTitle[MAX_LOADSTRING];                  // 标题栏文本
WCHAR szWindowClass[MAX_LOADSTRING];            // 主窗口类名

// 此代码模块中包含的函数的前向声明: 
ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
                     _In_opt_ HINSTANCE hPrevInstance,
                     _In_ LPWSTR    lpCmdLine,
                     _In_ int       nCmdShow)
{
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);

    // TODO: 在此放置代码。

    // 初始化全局字符串
    LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
    LoadStringW(hInstance, IDC_PLANTWARS, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    // 执行应用程序初始化: 
    if (!InitInstance (hInstance, nCmdShow))
    {
        return FALSE;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_PLANTWARS));

    MSG msg;

    // 主消息循环: 
    while (GetMessage(&msg, nullptr, 0, 0))
    {
        if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }

    return (int) msg.wParam;
}



//
//  函数: MyRegisterClass()
//
//  目的: 注册窗口类。
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
    WNDCLASSEXW wcex;
	//【1】窗口设计四部曲之一：开始设计一个完整的窗口类
    wcex.cbSize = sizeof(WNDCLASSEX);
	//设置结构体的字节大小
    wcex.style          = CS_HREDRAW | CS_VREDRAW;
	//设置窗口样式
    wcex.lpfnWndProc    = WndProc;
	//设置指向窗口过程处理函数的指针
    wcex.cbClsExtra     = 0;
	//窗口类的附加内存，取0就可以了
    wcex.cbWndExtra     = 0;
	//窗口的附加内存，同样取0
    wcex.hInstance      = hInstance;
	//指定包含窗口过程的程序的实例句柄
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_PLANTWARS));
	//软件的图标
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
	//指定窗口类的光标句柄
    wcex.hbrBackground  = (HBRUSH)(COLOR_WINDOW+1);
	//为hbrbackground成员指定一个灰色画刷句柄
   // wcex.lpszMenuName   = MAKEINTRESOURCEW(IDC_PLANTWARS);
	wcex.lpszMenuName   = nullptr; 
	//用一个以空终止的字符串，指定菜单资源的名字，去掉菜单
    wcex.lpszClassName  = szWindowClass;
	//用一个以空终止的字符串，指定窗口类的名字

    wcex.hIconSm        = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));
	//【2】窗口创建四部曲之二，注册窗口类
    return RegisterClassExW(&wcex);
}

//
//   函数: InitInstance(HINSTANCE, int)
//
//   目的: 保存实例句柄并创建主窗口
//
//   注释: 
//
//        在此函数中，我们在全局变量中保存实例句柄并
//        创建和显示主程序窗口。
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   hInst = hInstance; // 将实例句柄存储在全局变量中
   int cxScreen = GetSystemMetrics(SM_CXSCREEN);
   int cyScreen = GetSystemMetrics(SM_CYSCREEN);
   //【3】窗口创建四部曲之三：正式创建窗口
   HWND hWnd = CreateWindowW(szWindowClass,
							 szTitle, 
							 WS_OVERLAPPEDWINDOW & (~WS_MAXIMIZEBOX),
						     (cxScreen-WNDWIDTH)/2, 
						     (cyScreen - WNDHEIGHT+100)/2, 
						     WNDWIDTH, 
							 WNDHEIGHT,
							 nullptr,
							 nullptr, 
							 hInstance,
							 nullptr);

   if (!hWnd)
   {
      return FALSE;
   }

   ShowWindow(hWnd, nCmdShow);
   UpdateWindow(hWnd);

   return TRUE;
}

//
//  函数: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  目的:    处理主窗口的消息。
//
//  WM_COMMAND  - 处理应用程序菜单
//  WM_PAINT    - 绘制主窗口
//  WM_DESTROY  - 发送退出消息并返回
//
//
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    switch (message)
    {
    case WM_COMMAND:
        {
            int wmId = LOWORD(wParam);
            // 分析菜单选择: 
            switch (wmId)
            {
            case IDM_ABOUT:
                DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
                break;
            case IDM_EXIT:
                DestroyWindow(hWnd);
                break;
            default:
                return DefWindowProc(hWnd, message, wParam, lParam);
            }
        }
        break;
    case WM_PAINT:
        {
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hWnd, &ps);
            // TODO: 在此处添加使用 hdc 的任何绘图代码...
            EndPaint(hWnd, &ps);
        }
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}

// “关于”框的消息处理程序。
INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
    UNREFERENCED_PARAMETER(lParam);
    switch (message)
    {
    case WM_INITDIALOG:
        return (INT_PTR)TRUE;

    case WM_COMMAND:
        if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
        {
            EndDialog(hDlg, LOWORD(wParam));
            return (INT_PTR)TRUE;
        }
        break;
    }
    return (INT_PTR)FALSE;
}
