from pyniryo import *

robot = NiryoRobot("169.254.200.200")
robot.calibrate_auto()
robot.update_tool()
coords = []

with open("data.txt", "r") as file:
    coords = [float(x) for x in file.readline().split(",")]

print("coords : ", coords)
robot.move_joints(coords)
robot.wait(3)
robot.grasp_with_tool()
robot.move_joints(0.04492895347507231, -0.3853193845103182, -0.9779279560152725, 0.026170326983848913, -0.18110238656029853, 9.265358979293481e-05)
robot.release_with_tool()
robot.move_joints(0.04797280769055012, 0.279741817620625, -1.0142866549928184, -0.05206269319831902, -0.25780142595458067, 0.0016266343776782932)

robot.close_connection()
