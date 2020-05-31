// Jetson Nano.cpp: определяет точку входа для приложения.

#include <evhttp.h>
#include <memory>

#include "main.h"
#include "AfelBotConfig.h"

using namespace std;

int main()
{
	// Выводим текущую версию программы.
	cout << "AfelBot version " << AFELBOT_VERSION << endl;

    //Специальная инициализация для винды.
    #ifdef _WIN32
        WSADATA wsa_data;
        WSAStartup(0x0201, &wsa_data);
    #endif
        
    if (!event_init()) {
        cerr << "Ошибка инициализации LibEvent." << endl;
        return -1;
    }
    
    char const SrvAddress[] = "127.0.0.1";
    uint16_t SrvPort = 5555;
    unique_ptr<evhttp, decltype(&evhttp_free)> Server(evhttp_start(SrvAddress, SrvPort), &evhttp_free);
    if (!Server){
        cerr << "Не удалось запустить сервер HTTP" << endl;
        return -1;
    }
    void (*OnReq)(evhttp_request * req, void*) = [](evhttp_request* req, void*)
    {
        auto* OutBuf = evhttp_request_get_output_buffer(req);
        if (!OutBuf) {
            return;
        }
            
        evbuffer_add_printf(OutBuf, "<html><body><center><h1>Привет, мир!</h1></center></body></html>");
        evhttp_send_reply(req, HTTP_OK, "", OutBuf);
    };
    
    evhttp_set_gencb(Server.get(), OnReq, nullptr);
    if (event_dispatch() == -1)
    {
        cerr << "Не удалось запустить цикл обработки событий." << endl;
        return -1;
    }

	return 0;
}
