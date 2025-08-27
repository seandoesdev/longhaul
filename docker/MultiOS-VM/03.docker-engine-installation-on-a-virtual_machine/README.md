# 초기 계정 접속 정보
General User
username : vagrant
password : vagrant

Root User
username : root
password : vagrant

실제 셋팅 해서 사용하게 되는 경우, 쉘 스크립트에 새 계정 생성 및 패스워스 설정, root 패스워드 변경 추가 필요.


### vagrant status
![status](image.png)


### vagrant ssh [접속할 서버 이름]
![ssh 접속](image-1.png)

<br><br>

# 가상머신에 Docker 설치

## root 계정 변경
> passwd root

## SWAP 비활성화(swap 기능 끄고, 부팅 시 자동 스왑 켜지지 않도록 설정)
> swapoff -a && sed -i '/swap/s/^/#/' /etc/fstab

<details>
<summary>COMMAND 설명</summary>

- swapoff: 현재 사용 중인 모든 스왑 영역을 비활성
    - -a 옵션: /proc/swaps에 등록된 모든 스왑 장치/파일을 끕니다.
    - 실행 후에는 물리 메모리(RAM)만 사용하게 됩니다.
- && : 앞 명령이 잘 실행되면 뒤 명령어 실행
- sed -i '/swap/s/^/#/' /etc/fstab
    - sed: 파일 내용 변환(스트림 편집기)
    - -i: 파일을 직접 수정(인플레이스)
    - '/swap/...': swap이라는 단어가 들어간 라인을 찾아서
    - s/^/#/: 해당 라인의 맨 앞(^)에 #을 추가 → 주석 처리
ㄴ

</details>

<details>
<summary>SWAP</summary>

리눅스·유닉스 계열 운영체제의 가상 메모리 공간
- RAM이 부족할 때 디스크 공간 일부를 메모리처럼 사용하는 방식
- 스왑 영역에 저장된 데이터는 다시 필요할 때 RAM으로 가져옵니다
- 장점
- RAM 부족 상황에서 프로그램이 죽는 걸 방지
- 대규모 작업 시 안정성 확보
- 단점
- 디스크 속도가 RAM보다 훨씬 느려서 성능 저하 가능
- SSD 사용 시 잦은 쓰기 작업으로 수명 단축 가능

</details>

## 노드간 통신을 위한 Bridge 설정(Iptables 커널 옵션 활성화)
> cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf     <br>
> br_netfilter                                          <br>
> EOF                                                   <br>
> cat <<EOF>>  /etc/sysctl.d/k8s.conf                   <br>
> net.bridge.bridge-nf-call-ip6tables = 1               <br>
> net.bridge.bridge-nf-call-iptables = 1                <br>
> EOF

<details>
<summary>COMMAND 설명</summary>

> cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf     <br>
> br_netfilter                                          <br>
> EOF                                                   <br>

- `br_netfilter` 모듈을 커널에 로드하도록 설정하는 명령.
- `br_netfilter`는 **브리지 네트워크를 통해 전달되는 패킷도 iptables로 필터링할 수 있게 해주는 커널 모듈**.
- 이 설정은 `/etc/modules-load.d/k8s.conf` 파일에 저장되며, 시스템 부팅 시 자동으로 로드.

> cat <<EOF >> /etc/sysctl.d/k8s.conf       <br>
> net.bridge.bridge-nf-call-ip6tables = 1   <br>
> net.bridge.bridge-nf-call-iptables = 1    <br>
> EOF

- `sysctl` 설정을 통해 **브리지 네트워크를 통한 IPv4 및 IPv6 패킷도 iptables 규칙에 따라 처리되도록 활성화**합니다.
- `net.bridge.bridge-nf-call-iptables = 1`: IPv4 패킷에 대해 iptables 필터링 활성화
- `net.bridge.bridge-nf-call-ip6tables = 1`: IPv6 패킷에 대해 ip6tables 필터링 활성화
- 이 설정은 `/etc/sysctl.d/k8s.conf`에 저장되며, `sysctl --system` 명령으로 적용.

위 커맨드는 내부적으로 브리지 네트워크를 사용하여 Pod 간 통신을 처리하기 위함. 이때 iptables를 통해 네트워크 정책이나 트래픽 제어를 하려면 위 설정이 반드시 필요.

addition
- `cat <<EOF ... EOF` 구문은 `여러 줄의 텍스트를 한 번에 입력`해서 파일로 저장하거나 명령어에 전달할 때 사용.
- `tee`는 표준 입력을 받아서 파일에 저장하면서 동시에 화면에도 출력하는 명령어.
    - `sudo tee /path/to/file`는 `>` 리디렉션보다 안전하게 권한 있는 파일을 수정할 수 있게 해줌.
- `etc/sysctl.d` 이 디렉토리는 커널 파라미터 설정 파일들을 저장하는 위치.
    - `sysctl`은 리눅스 커널의 동작을 제어하는 설정 도구이고, `/etc/sysctl.d/*.conf`에 설정을 넣으면 **부팅 시 자동 적용**.
    - 즉시 적용은 `sysctl --system`
    - 네트워크, 메모리, 파일 핸들 등 시스템 동작에 영향을 주는 설정

</details>

## host 설정
> vi /etc/hosts <br>
> 192.168.32.10 k8s-master <br>
> 192.168.32.11 k8s-node01 <br>
> 192.168.32.12 k8s-node02 <br>

<span style="color: #787373ff;">Rocky Linux를 다르게 진행
[참고](https://docs.rockylinux.org/gemstones/containers/docker/)
</span>
## Docker 설치를 위한 필수 패키지 설치
> dnf -y install ca-certificates curl gnupg net-tools

## Docker repository 추가
> sudo dnf config-manager --add-repo https://download.docker.com/linux/rhel/docker-ce.repo

## Docker & Docker-compose 설치
> sudo dnf update

> sudo dnf -y install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

## Docker 시작 및 부팅 시 자동 시작
> sudo systemctl --now enable docker

## sudo 없이 docker 사용할 수 있도록 유저 설정
> **현재 유저 추가**              <br>
sudo usermod -a -G docker $(whoami)     <br>
**특정 유저 추가**                 <br>
sudo usermod -a -G docker custom-user