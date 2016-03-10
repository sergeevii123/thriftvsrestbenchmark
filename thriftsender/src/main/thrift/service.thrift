namespace java info.developerblog.services.user

struct THandlerResponse {
    1: required binary file,
    2: required i64 start
}

struct TFileAndStart {
    1: required binary file,
    2: required i64 start
}

service TBenchmarkService {
    THandlerResponse getfile()
    void sendFile(1: TFileAndStart t)
}